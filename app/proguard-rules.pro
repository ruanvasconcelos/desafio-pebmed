# The cause is that R8 tries to remove the method and inline it at the single call site.
# So, if our app calls RunnableDisposable.constructor from multiple places, this issue doesn't happen.
# FORCE inlining on non-inlinable: void io.reactivex.rxjava3.disposables.RunnableDisposable.constructor$io$reactivex$rxjava3$disposables$ReferenceDisposable(java.lang.Object)
-keep class io.reactivex.rxjava3.disposables.RunnableDisposable { <init>(...); }

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
# Missing class: org.conscrypt.ConscryptHostnameVerifier
-dontwarn org.conscrypt.ConscryptHostnameVerifier