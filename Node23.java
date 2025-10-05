public class Node23 {
    int[] chaves = new int[2];    
    Node23[] filhos = new Node23[3];  
    int n;                         
    boolean folha = true;   

    boolean cheio() { return n == 2; }
}
