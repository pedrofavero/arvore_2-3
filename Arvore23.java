public class Arvore23 {

    private Node23 raiz = null;

     public void inserir(int valor) {
        if (raiz == null) {
            raiz = new Node23();
            raiz.chaves[0] = valor;
            raiz.n = 1;
            return;
        }

        if (raiz.cheio()) {
            Node23 nova = new Node23();
            nova.folha = false;
            nova.filhos[0] = raiz;
            dividirFilho(nova, 0);
            raiz = nova;
        }
        inserirNaoCheio(raiz, valor);
    }

    private void inserirNaoCheio(Node23 no, int valor) {
        int i = no.n - 1;
        if (no.folha) {
            // desloca chaves para abrir espaço
            while (i >= 0 && valor < no.chaves[i]) {
                no.chaves[i + 1] = no.chaves[i];
                i--;
            }
            no.chaves[i + 1] = valor;
            no.n++;
        } else {
            while (i >= 0 && valor < no.chaves[i]) i--;
            i++;
            if (no.filhos[i].cheio()) {
                dividirFilho(no, i);
                if (valor > no.chaves[i]) i++;
            }
            inserirNaoCheio(no.filhos[i], valor);
        }
    }

     private void dividirFilho(Node23 pai, int i) {
        Node23 cheio = pai.filhos[i];
        Node23 novo = new Node23();
        novo.folha = cheio.folha;

        // sobe a chave da direita
        pai.chaves[i] = cheio.chaves[0];
        pai.n++;

        // novo recebe a chave da direita do nó cheio
        novo.chaves[0] = cheio.chaves[1];
        novo.n = 1;

        // se não for folha, move filhos
        if (!cheio.folha) {
            novo.filhos[0] = cheio.filhos[1];
            novo.filhos[1] = cheio.filhos[2];
        }

        cheio.n = 1; // fica só com a primeira chave

        // insere novo filho no pai
        for (int j = pai.n; j > i + 1; j--) {
            pai.filhos[j] = pai.filhos[j - 1];
            pai.chaves[j] = pai.chaves[j - 1];
        }
        pai.filhos[i + 1] = novo;
    }

     public void remover(int valor) {
        removerInterno(raiz, valor);
        if (raiz != null && raiz.n == 0 && !raiz.folha) {
            raiz = raiz.filhos[0]; // ajusta caso a raiz fique vazia
        }
    }

    private void removerInterno(Node23 no, int valor) {
        if (no == null) return;

        int i = 0;
        while (i < no.n && valor > no.chaves[i]) i++;

        if (i < no.n && no.chaves[i] == valor) {
            if (no.folha) {
                // remove da folha
                for (int j = i; j < no.n - 1; j++) no.chaves[j] = no.chaves[j + 1];
                no.n--;
            } else {
                // simplificado: pega sucessor e substitui
                Node23 aux = no.filhos[i + 1];
                while (!aux.folha) aux = aux.filhos[0];
                no.chaves[i] = aux.chaves[0];
                removerInterno(no.filhos[i + 1], aux.chaves[0]);
            }
        } else if (!no.folha) {
            removerInterno(no.filhos[i], valor);
        }
    }

     public void imprimir() {
        imprimirRec(raiz);
        System.out.println();
    }

    private void imprimirRec(Node23 no) {
        if (no == null) return;
        if (no.folha) {
            for (int i = 0; i < no.n; i++) System.out.print(no.chaves[i] + " ");
        } else {
            for (int i = 0; i < no.n; i++) {
                imprimirRec(no.filhos[i]);
                System.out.print(no.chaves[i] + " ");
            }
            imprimirRec(no.filhos[no.n]);
        }
    }
}
