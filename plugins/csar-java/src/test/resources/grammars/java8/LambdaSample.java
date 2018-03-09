import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Lambdas {
    public static void main(String[] args) {
        // use predicate composition to remove matching names
        List<Name> list = new ArrayList<>();
        for (Name name : NAMES) {
            list.add(name);
        }
        Predicate<Name> pred1 = name -> "Sally".equals(name.firstName);
        Predicate<Name> pred2 = name -> "Queue".equals(name.lastName);
        list.removeIf(pred1.or(pred2));
        printNames("Names filtered by predicate:", list.toArray(new Name[list.size()]));

        Comparator<Name> com1 = Comparator.comparing((Name name1) -> name1.lastName)
                .thenComparing(name2 -> name2.firstName);
        Comparator<Name> com2 = Comparator.<Name,String>comparing(name1 -> name1.lastName)
                .thenComparing(name2 -> name2.firstName);

        // sort array using lambda expression
        copy = Arrays.copyOf(NAMES, NAMES.length);
        Arrays.sort(copy, (a, b) -> a.compareTo(b));
        printNames("Names sorted with lambda expression:", copy);
    }
}
