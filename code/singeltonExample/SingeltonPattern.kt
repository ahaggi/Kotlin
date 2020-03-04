// ¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤   The first approach ¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤
// 1- we can define a class (DbConnection_A) that we want to use it as "Singelton"; without any "object expression" nor "companion object".
// 2- create an "object declaration" (object SingeltonCreator{..}) which has an instance of the class "DbConnection_A" as a member. 
// 3- we can obtain a singelton of the class "DbConnection_A" as flwg: let a:DbConnection_A = SingeltonCreator.instance
class DbConnection_A (var token:String) {
    init{
        println(token)
    }
    var someVariable = "value"
}

// Object declaration
object SingeltonClass{
    // "object" or "companion object" is instantiated when it is first used. vals and vars in an "object" are initialized when the "object" is first instantiated (i.e., when the object is first used).
    // "DbConnection(..)" will initialized just once!

    // we could use "object DbConnection{...}" But then we couldn't pass any args, because constructor is not allowed in an "object"
    
    var instance = DbConnection_A("@@@@@ Db_connection is initialized! @@@@")
}

fun fstApproach(){
    println("We will create the 1. instance now..")
    var firstInstance:DbConnection_A = SingeltonClass.instance
    firstInstance.someVariable.also (::println)
    firstInstance.someVariable = "new value".also { println("we will change 'someVariable' to be '$it' ") }

    println("We will create the 2. instance now..")
    var secondInstance:DbConnection_A = SingeltonClass.instance
    secondInstance.someVariable.also (::println)

}


/************************************************************************************************ */
/************************************************************************************************ */

// ¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤   The second approach ¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤¤
// 1- we can define a class (DbConnection_B) that we want to use it as "Singelton"; with a member "object expression" or "companion object". let's call it "DbConnection_B"
// 2- we can obtain a singelton of the class "DbConnection_B" as flwg: let a:DbConnection_B = DbConnection_B.instance

class DbConnection_B private constructor(var token:String) {
    init{
        println(token)
    }
    var someVariable = "value"

    companion object {
        var instance:(String)->DbConnection_B = ::DbConnection_B 
        // we could also use
        //      var instance = DbConnection_B("@@@@@ Db_connection is initialized! @@@@")
        // and we can create an instance of "DbConnection_B" with
        //      var firstInstance:DbConnection_B = DbConnection_B.instance

    }

}

fun sndApproach(){
    println("We will create the 1. instance now..")
    var firstInstance:DbConnection_B = DbConnection_B.instance("@@@@@ Db_connection is initialized! @@@@")
    firstInstance.someVariable.also (::println)
    firstInstance.someVariable = "new value".also { println("we will change 'someVariable' to be '$it' ") }

    println("We will create the 2. instance now..")
    var secondInstance:DbConnection_B = DbConnection_B.instance("@@@@@ Db_connection is initialized! @@@@")
    secondInstance.someVariable.also (::println)
}


fun main(args: Array<String>) {
    // To execute, 
    // 1- Download "kotlinc" and add the "downloadedFolder/bin" to the "Path-variable" 
    // 2- run the flwg command: 
    // "kotlinc SingeltonPattern.kt -include-runtime -d SingeltonPattern.jar ; java -jar SingeltonPattern.jar"
    // Or
    // "kotlinc SingeltonPattern.kt -include-runtime -d SingeltonPattern.jar && java -jar SingeltonPattern.jar"

    fstApproach()

    println("*****************************************************")
    println("*****************************************************")

    sndApproach()


    


}
