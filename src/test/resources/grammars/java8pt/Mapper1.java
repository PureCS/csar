class Mapper1 {
    // The class itself is not generic, the constructor is
    <T, V> Mapper(T array, V item) {
    }

    /* This method will accept only arrays of the same type as
    the searched item type or its subtype*/
    static <T, V extends T> boolean contains(T item, V[] arr) {
        for (T currentItem : arr) {
            if (item.equals(currentItem)) {
                return true;
            }
        }
        return false;
    }
}
