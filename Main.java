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
    }
    
}
