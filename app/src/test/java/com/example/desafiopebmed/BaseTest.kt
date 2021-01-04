package com.example.desafiopebmed

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import io.mockk.unmockkAll
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.concurrent.AbstractExecutorService
import java.util.concurrent.TimeUnit

/**
 * Classe base para testes unitários e instrumentados. Esta classe não deve ser estendida diretamente pelas classes de teste. Favor estender
 * [BaseUnitTest], [BaseFragmentTest] ou [BaseActivityTest].
 */
abstract class BaseTest {
    protected val gson = Gson()
    protected val mockWebServer = MockWebServer()
    protected val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .dispatcher(Dispatcher(object : AbstractExecutorService() {
            override fun shutdown() {
            }

            override fun isTerminated(): Boolean {
                return false
            }

            override fun isShutdown(): Boolean {
                return false
            }

            override fun shutdownNow(): List<Runnable>? {
                return null
            }

            @Throws(InterruptedException::class)
            override fun awaitTermination(l: Long, timeUnit: TimeUnit): Boolean {
                return false
            }

            override fun execute(runnable: Runnable) {
                runnable.run()
            }
        }))
        .addInterceptor(HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                println("okHttpClient $message")
            }

        }).setLevel(HttpLoggingInterceptor.Level.BODY))
        .writeTimeout(3, TimeUnit.SECONDS)
        .readTimeout(3, TimeUnit.SECONDS)
        .connectTimeout(3, TimeUnit.SECONDS)
        .build()

    protected val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/").toString())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    open fun before() {
    }

    @After
    open fun after() {
        unmockkAll()
    }

    /**
     * Insere um arquivo json na fila de Responses do MockWebServer.
     *
     * @param responsePath O caminho do arquivo json. Ele deve ficar dentro da pasta Resources
     * do sourceset onde está a classe sendo testada.
     *
     * @param code Response code opcional a ser usado pelo servidor mockado. O default é 200.
     */
    fun enqueueResponse(responsePath: String? = null, code: Int = 200) {
        mockWebServer.enqueue(MockResponse().setBody(responsePath?.let { getJsonAsString(it) }
            ?: "dummyTest").setResponseCode(code))
    }

    /**
     * Determina Responses (arquivos json) que o MockWebServer deve usar quando os Requests
     * recebidos por ele contiverem determinadas strings. Essa função dever ser usada quando não
     * pudermos enfileirar Responses no MockWebServer em uma ordem pré-determinada devido a Requests
     * executados em paralelo, sem conhecermos a ordem em que eles serão processados pelo
     * MockWebServer.
     *
     * Caso algum Request não contenha nenhuma das Strings passadas para este método, o
     * MockWebServer responderá com code 404 e body vazio.
     *
     * @param triple Um ou mais triples que contém:
     *
     *      triple.first: a String que deve existir no Request;
     *
     *      triple.second: o caminho do json que será usado como Response. Ele deve ficar dentro da
     *      pasta Resources do sourceset onde está a classe
     *      sendo testada.
     *
     *      triple.third: o Response code a ser usado.
     */
    fun enqueueAsyncResponses(vararg triple: Triple<String, String, Int?>) {
        mockWebServer.dispatcher = object : okhttp3.mockwebserver.Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                triple.asList().forEach {
                    if (request.path?.contains(it.first) == true) {
                        return MockResponse().setResponseCode(
                            it.third
                                ?: 200
                        ).setBody(getJsonAsString(it.second))
                    }
                }

                return MockResponse().setResponseCode(404).setBody("")
            }
        }
    }

    protected fun getJsonAsString(responsePath: String): String {
        val iStream: InputStream? = openResource(responsePath)
        return readFile(iStream)
    }

    protected fun readFile(inputStream: InputStream?): String {
        try {
            val builder = StringBuilder()
            val reader = InputStreamReader(inputStream!!, "UTF-8")
            reader.readLines().forEach {
                builder.append(it)
            }
            return builder.toString()
        } catch (e: IOException) {
            throw RuntimeException("Cannot read file from specified path", e)
        }
    }

    private fun openResource(filename: String): InputStream? {
        return javaClass.classLoader?.getResourceAsStream(filename)
    }

    protected class ViewModelFactory(private vararg val parameterTypes: Any) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(*parameterTypes.map { it.javaClass }.toTypedArray())
                .newInstance(*parameterTypes)
        }
    }
}