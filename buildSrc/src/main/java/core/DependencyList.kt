package core

open class DependencyList {

    fun list(): List<String>{
        val list = mutableListOf<String>()
        this::class.java.methods.filter {
            it.name.startsWith("get")
        }.forEach {
            if(it.returnType.name == "java.lang.String")
                list.add(it.invoke(this).toString())
        }
        return list
    }

}