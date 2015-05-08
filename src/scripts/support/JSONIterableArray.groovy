package scripts.support

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONTokener

/**
 * @author Lawrence Thatcher
 */
class JSONIterableArray extends JSONArray implements Iterable
{
    JSONIterableArray(JSONArray array)
    {
        super(array.toString())
    }

    JSONIterableArray() {
    }

    JSONIterableArray(JSONTokener x) throws JSONException {
        super(x)
    }

    JSONIterableArray(String source) throws JSONException {
        super(source)
    }

    JSONIterableArray(Collection<Object> collection) {
        super(collection)
    }

    JSONIterableArray(Object array) throws JSONException {
        super(array)
    }

    @Override
    Iterator iterator()
    {
        return new JSONArrayIterator()
    }

    class JSONArrayIterator implements Iterator
    {
        int index = -1;

        @Override
        boolean hasNext()
        {
            int next = index + 1;
            return next < length();
        }

        @Override
        Object next()
        {
            index++
            return get(index)
        }
    }
}
