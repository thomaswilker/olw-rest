package olw.model.view.helper;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MappingHelper {

	public static <T1,T2, C1 extends Collection<T1>, C2 extends Collection<T2>> C2 toViewCollection(Function<T1,T2> f, C1 collection, Supplier<C2> collectionFactory) {
		return collection.stream().map(e -> f.apply(e)).collect(Collectors.<T2,C2>toCollection(collectionFactory));
	}
	
	
	public static <T1,T2> T2 ifNN(T1 value, Function<T1,T2> f) {
		if(value != null)
			return f.apply(value);
		else
			return null;
	}
	
}
