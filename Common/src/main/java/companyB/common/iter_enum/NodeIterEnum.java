package companyB.common.iter_enum;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * This class implements both java.util.Iterator and java.util.Enumeration.
 * Internally, it is a primitive node-based singly linked list.
 * @param <E>
 * @author C.A. Burrell deltafront@gmail.com
 */
@SuppressWarnings("PMD.UselessParentheses")
public class NodeIterEnum<E> implements Enumeration<E>, Iterator<E>
{
    private node<E> head;

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
        return (hasUnmarkedNodes(head));
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
        return hasMoreElements();
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
     * This method is not implemented.
     * Calling this method will throw a UnsupportedOperationException.
     * @since 1.0
     * @throws UnsupportedOperationException
     */
    public void remove()
    {
        //remove the current node
        //node temp = current.next
        //current == null
        //current = temp
        throw new UnsupportedOperationException("This operation has not been implemented");
    }

    private E _nextElement()
    {
        if (!hasMoreElements())
        {
            return null;
        }
        else
        {
            return getUnMarkedNode(head);
        }
    }

    private E getUnMarkedNode(node<E> _node)
    {
        if (!_node.marked)
        {
            _node.marked = true;
            return _node._this;
        }
        else
        {
            return getUnMarkedNode(_node.next);
        }
    }

    private boolean hasUnmarkedNodes(node<E> _node)
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
            return hasUnmarkedNodes(_node.next);
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