package id.tunjukin.jetpackcompose

class InputModel(var items: MutableList<ItemPoint>){

    class ItemPoint(
        var id: Long = 0,
        var name: String,
        var checked: Boolean = false
    ) {
    }
}