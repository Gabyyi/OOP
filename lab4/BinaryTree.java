package lab4;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("unused")
public class BinaryTree<T extends Comparable<T>>{
   private Node<T> root;

   public void add(T val){
       root=insertRec(root, val);
   }

   private Node<T> insertRec(Node<T> root, T val){
        if(root==null){
            root=new Node<>(val);
            return root;
        }
        if(val.compareTo(root.val)<0){
            root.left=insertRec(root.left,val);
        }else if(val.compareTo(root.val)>0){
            root.right=insertRec(root.right,val);
        }
        return root;
    }

    public void inOrder(){
        inOrderRec(root);
        System.out.println();
    }

    private void inOrderRec(Node<T> curr){
        if(curr!=null){
            inOrderRec(curr.left);
            System.out.print(curr.val+" ");
            inOrderRec(curr.right);
        }
    }

    private static class Node<T>{
        T val;
        Node<T> left,right;

        public Node(T val){
            this.val=val;
            left=right=null;
        }
    }
}