package com.kneeremover.extendedconsumables.util;

import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import java.util.concurrent.ThreadLocalRandom;

public class Randomizer { // From stackoverflow (https://stackoverflow.com/a/65047662)
	public static <E> E getRandomElementFromCollection(Collection<E> collection)
	{
		if(collection.isEmpty())
			return null;

		int randomIndex = ThreadLocalRandom.current().nextInt(collection.size());

		if(collection instanceof RandomAccess)
			return ((List<E>) collection).get(randomIndex);

		for(E element : collection)
		{
			if(randomIndex == 0)
				return element;

			randomIndex--;
		}

		throw new IllegalStateException("How did we get here?"); //unreachable
	}
}
