// https://medium.com/@BladeCoder/kotlin-singletons-with-argument-194ef06edd9e
// https://kotlinlang.org/docs/reference/generics.html#variance

fun main(args: Array<String>) {
    // To execute, 
    // 1- Download "kotlinc" and add the "downloadedFolder/bin" to the "Path-variable" 
    // 2- run the flwg command: 
    // "kotlinc 3rdApproachSingletonPattern.kt -include-runtime -d 3rdApproachSingletonPattern.jar ; java -jar 3rdApproachSingletonPattern.jar"
    // Or
    // "kotlinc 3rdApproachSingletonPattern.kt -include-runtime -d 3rdApproachSingletonPattern.jar && java -jar 3rdApproachSingletonPattern.jar"
    Db.getInstance("123")

}

// Notice how this class doesn't have "getInstance(args)" But it has an "companion object" with an "anonymous object" which extends SingletonCreator
// object expression: used to create anonymous objects. They are used if you need to create an object of a slight modification of some class or interface without declaring a subclass for it.
class Db private constructor (auth: String) {
    lateinit var auth: String
    init {
        println("The auth value is $auth")
    }

    // create an anonymous object which extend the "SingletonCreator" super-class
    companion object : SingletonCreator<Db, String>(creator = ::Db)
}

// Notice open keyword
open class SingletonCreator<out T, in Any>(creator: (args: Any) -> T) {
    // creator will hold the refrence to some constructor
    private var creator: ((Any) -> T)? = creator
    @Volatile private var instance: T? = null

    fun getInstance(args: Any): T {
        // the next "if" is not inside a synchronized-block, because we don't need to block any thread of getting an already intialized instance. 
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!(args)
                instance = created
                creator = null
                created
            }
        }
    }
}
