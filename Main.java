public class Main {

    public static void main(String[] args) {
        Arvore23 arvore = new Arvore23();

        arvore.inserir(10);
        arvore.inserir(20);
        arvore.inserir(5);
        arvore.inserir(15);
        arvore.inserir(25);

        System.out.print("Árvore após inserções: ");
        arvore.imprimir();

        arvore.remover(15);
        System.out.print("Árvore após remover 15: ");
        arvore.imprimir();

        // ----------------- ADICIONAl -----------------
        // arvore.inserir(30);
        // arvore.inserir(2);
        // arvore.inserir(8);
        // arvore.inserir(12);
        // arvore.inserir(18);
        // arvore.inserir(22);
        // arvore.inserir(28);
        // arvore.inserir(35);
        // System.out.print("Após inserções adicionais: ");
        // arvore.imprimir();

        // arvore.remover(5);   
        // System.out.print("Após remover 5 (folha): ");
        // arvore.imprimir();

        // arvore.remover(10);   
        // System.out.print("Após remover 10 (interno): ");
        // arvore.imprimir();

        // arvore.inserir(0);    
        // arvore.inserir(100);   
        // System.out.print("Após inserir extremos {0, 100}: ");
        // arvore.imprimir();

        // arvore.remover(0);
        // arvore.remover(100);
        // System.out.print("Após remover extremos {0, 100}: ");
        // arvore.imprimir();
    }
    
}
