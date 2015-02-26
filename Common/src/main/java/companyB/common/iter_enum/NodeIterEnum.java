package companyB.common.iter_enum;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * This class implements both java.util.Iterator and java.util.Enumeration.
 * Internally, it is a primitive node-based singly linked list.
 *
 * @param <E>
 * @author C.A. Burrell deltafront@gmail.com
 */
@SuppressWarnings("PMD.UselessParentheses")
public class NodeIterEnum<E> implements Enumeration<E>, Iterator<E>
{
    private node<E> head;
    private boolean nextFlag;

    public NodeIterEnum()
    {
        head = null;
    }

    /**
     * @return true if there are more elements to return
     * @since 1.0
     */
    public boolean hasMoreElements()
    {
        return _hasUnmarkedNodes(head);
    }

    /**
     * @return the next element in the order to which they were added to the list (FiFo)
     * @since 1.0
     */
    public E nextElement()
    {
        return _nextElement();
    }

    /**
     * @return true if there are more elements to return
     * @since 1.0
     */
    public boolean hasNext()
    {
        return _hasUnmarkedNodes(head);
    }

    /**
     * @return the next element in the order to which they were added to the list (FiFo)
     * @since 1.0
     */
    public E next()
    {
        return _nextElement();
    }

    /**
     * Adds an element to the list
     *
     * @param in element that is added
     * @since 1.0
     */
    public void add(E in)
    {
        if (head == null)
        {
            head = new node<E>(in);
        }
        else
        {
            head.addNext(in);
        }
    }

    /**
     * Removes next element from consideration.
     * @throws java.lang.IllegalStateException If at least one call to 'next' has not been made prior to the current
     * invocation of this method.
     * @since 1.0
     */
    public void remove()
    {
        if(!nextFlag)
        {
            throw new IllegalStateException("A call to 'next' needs to be made before a call to remove.");
        }
        if(hasMoreElements())
        {
            _nextElement();
        }
        nextFlag = false;
    }

    private E _nextElement()
    {
        nextFlag = true;
        if (!hasMoreElements())
        {
            return null;
        }
        else
        {
            return _getUnMarkedNode(head);
        }
    }

    private E _getUnMarkedNode(node<E> _node)
    {
        if (!_node.marked)
        {
            _node.marked = true;
            return _node._this;
        }
        else
        {
            return _getUnMarkedNode(_node.next);
        }
    }

    private boolean _hasUnmarkedNodes(node<E> _node)
    {
        if (_node == null)
        {
            return false;
        }
        else if (!_node.marked)
        {
            return true;
        }
        else
        {
            return _hasUnmarkedNodes(_node.next);
        }
    }
}

class node<E>
{
    E _this;
    node<E> next;
    boolean marked = false;

    node(E in)
    {
        _this = in;
        next = null;
    }

    void addNext(E _next)
    {
        if (next == null)
        {
            next = new node<E>(_next);
        }
        else
        {
            next.addNext(_next);
        }
    }
}