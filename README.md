# ÁRVORE 2-3

## Como executar

- Clone o repositório no caminho que prefir do seu computador
- Abra em um editor de código
- No terminal dentro do caminho que salvou o projeto rode:
  - javac Main.java
  - java Main.java
- Caso queira adicionar mais massa, descomente o código ou altere que está no arquivo Main.java
 
## Estrutura do nó — `Node23.java`

```java
// A classe Node23 representa um nó da árvore 2-3.
// Cada nó pode ter até 2 chaves (valores) e até 3 filhos.
// Isso permite que a árvore permaneça balanceada e eficiente.

public class Node23 {
    int[] chaves = new int[2];      // guarda até 2 valores ordenados dentro do nó
    Node23[] filhos = new Node23[3]; // referências para até 3 filhos
    int n;                           // quantidade atual de chaves no nó (0, 1 ou 2)
    boolean folha = true;            // indica se o nó é folha (não tem filhos)

    // Método auxiliar para verificar se o nó está cheio (já possui 2 chaves)
    boolean cheio() { return n == 2; }
}

// Explicação rápida:
// - chaves[] : armazena os valores ordenados do nó.
// - filhos[] : aponta para subárvores (quando o nó não é folha).
// - n        : conta quantas chaves estão ocupadas no nó.
// - folha    : true se não possui filhos, false se possui.
// - cheio()  : ajuda a saber quando será necessário dividir o nó ao inserir.
```

## `public void inserir(int valor)`

```java
// Insere um novo valor na árvore 2-3 mantendo o balanceamento.
// Estratégia top-down: se a raiz estiver cheia, divide antes de descer.
public void inserir(int valor) {
    // Caso 1: árvore vazia → cria a raiz com a primeira chave
    if (raiz == null) {
        raiz = new Node23();
        raiz.chaves[0] = valor; // coloca o valor na primeira posição
        raiz.n = 1;             // agora a raiz tem 1 chave
        return;
    }

    // Caso 2: raiz cheia (tem 2 chaves) → árvore "cresce para cima"
    // Criamos uma nova raiz, penduramos a antiga como filho[0],
    // dividimos esse filho (split) e só então descemos para inserir.
    if (raiz.cheio()) {
        Node23 novaRaiz = new Node23();
        novaRaiz.folha = false;       // nova raiz é interna
        novaRaiz.filhos[0] = raiz;    // antiga raiz vira filho esquerdo
        dividirFilho(novaRaiz, 0);    // divide o filho cheio antes de descer
        raiz = novaRaiz;              // atualiza ponteiro da raiz
    }

    // Caso 3: raiz não está cheia → insere recursivamente no lugar certo
    inserirNaoCheio(raiz, valor);
}

```

Ideia-chave: nunca descemos para um nó cheio; se estiver cheio, dividimos primeiro. Isso garante que sempre exista espaço para inserir quando chegarmos à folha.


## `private void inserirNaoCheio(Node23 no, int valor)`

```java
// Insere 'valor' em um nó que NÃO está cheio.
// Regras:
//  - Se 'no' é folha: coloca o valor na posição correta, deslocando as chaves maiores.
//  - Se 'no' é interno: escolhe o filho correto para descer.
//      * Antes de descer, se o filho escolhido estiver cheio, fazemos o split (dividirFilho).
//      * Depois do split, decidimos se vamos para o filho da esquerda ou da direita da chave promovida.
private void inserirNaoCheio(Node23 no, int valor) {
    int i = no.n - 1; // índice da última chave ocupada no nó

    if (no.folha) {
        // Caso folha: abre espaço deslocando chaves maiores para a direita
        while (i >= 0 && valor < no.chaves[i]) {
            no.chaves[i + 1] = no.chaves[i];
            i--;
        }
        // Insere o novo valor na posição correta
        no.chaves[i + 1] = valor;
        no.n++; // incrementa a contagem de chaves do nó
    } else {
        // Caso interno: encontra o filho por onde devemos descer
        while (i >= 0 && valor < no.chaves[i]) i--;
        i++; // 'i' agora é o índice do filho adequado

        // Se o filho escolhido está cheio, dividimos antes de descer
        if (no.filhos[i].cheio()) {
            dividirFilho(no, i);

            // Após a divisão, uma chave do filho subiu para 'no'. Precisamos
            // decidir se continuamos pelo filho à esquerda (i) ou pela direita (i+1).
            if (valor > no.chaves[i]) i++;
        }

        // Desce recursivamente para inserir no filho que agora tem espaço
        inserirNaoCheio(no.filhos[i], valor);
    }
}
```


## `private void dividirFilho(Node23 pai, int i)`

```java
// Divide um nó cheio em dois nós menores e sobe uma chave para o pai.
// Essa função é a responsável por manter a árvore 2-3 balanceada durante inserções.
private void dividirFilho(Node23 pai, int i) {
    Node23 cheio = pai.filhos[i];   // nó filho que está cheio (2 chaves)
    Node23 novo = new Node23();     // novo nó que ficará à direita
    novo.folha = cheio.folha;       // novo nó é folha se o antigo também era

    // A primeira chave do nó cheio será promovida para o pai.
    int promovida = cheio.chaves[0];

    // O novo nó recebe a chave da direita do nó cheio.
    novo.chaves[0] = cheio.chaves[1];
    novo.n = 1;

    // Caso o nó não seja folha, redistribuímos os filhos:
    // - O nó original fica com os filhos da esquerda
    // - O novo nó recebe os filhos da direita
    if (!cheio.folha) {
        novo.filhos[0] = cheio.filhos[1];
        novo.filhos[1] = cheio.filhos[2];
        cheio.filhos[1] = null;
        cheio.filhos[2] = null;
    }

    // O nó original agora passa a ter apenas 1 chave
    cheio.n = 1;

    // No pai, abrimos espaço para inserir a chave promovida e o ponteiro para o novo nó
    for (int j = pai.n; j > i; j--) {
        pai.chaves[j] = pai.chaves[j - 1];
        pai.filhos[j + 1] = pai.filhos[j];
    }

    // Inserimos a chave promovida no pai
    pai.chaves[i] = promovida;
    pai.filhos[i + 1] = novo;
    pai.n++;
    pai.folha = false;
}
```

Fluxo da função:

Identifica o filho cheio que será dividido.

Cria um novo nó que ficará à direita do nó cheio.

Promove a chave da esquerda para o pai, que passa a apontar para o novo nó.

Redistribui filhos caso o nó não seja folha.

Garante que o pai agora tenha espaço para continuar inserindo valores.

A chave promovida sobe para o pai e o nó é dividido em dois filhos balanceados.

## `private void removerInterno(Node23 no, int valor)`

```java
// Remove um valor começando a partir do nó recebido.
// Estratégia simplificada para fins de aprendizado:
//  - Se o valor está em um nó folha: remove direto deslocando chaves.
//  - Se o valor está em um nó interno: substitui pelo sucessor (menor valor da subárvore à direita) e remove recursivamente.
//  - Se o valor não está neste nó: desce para o filho correto e continua a busca/remoção.
private void removerInterno(Node23 no, int valor) {
    if (no == null) return;

    int i = 0;
    // Avança até encontrar a posição onde o valor deveria estar ou já está
    while (i < no.n && valor > no.chaves[i]) i++;

    // CASO 1: Valor encontrado no nó atual
    if (i < no.n && no.chaves[i] == valor) {
        if (no.folha) {
            // 1A: Nó é folha → basta remover deslocando as chaves para a esquerda
            for (int j = i; j < no.n - 1; j++) no.chaves[j] = no.chaves[j + 1];
            no.n--;
        } else {
            // 1B: Nó é interno → substitui pelo sucessor
            // O sucessor é o menor valor da subárvore à direita do valor removido
            Node23 aux = no.filhos[i + 1];
            while (!aux.folha) aux = aux.filhos[0]; // vai para o menor nó da direita
            int sucessor = aux.chaves[0];

            // Substitui o valor atual pelo sucessor
            no.chaves[i] = sucessor;
            // Remove o sucessor recursivamente (ele estará em uma folha)
            removerInterno(no.filhos[i + 1], sucessor);
        }
    } 
    // CASO 2: Valor não está neste nó
    else {
        if (no.folha) return; // não encontrado (chegou em folha e não tem o valor)
        // Desce para o filho correto para continuar procurando/removendo
        removerInterno(no.filhos[i], valor);
    }
}
```
Fluxo da função:

Busca a posição onde o valor deveria estar.

Se encontrou no nó atual:

Folha: remove deslocando as chaves para preencher o espaço.

Interno: substitui pelo sucessor (menor valor da subárvore direita) e remove esse sucessor lá embaixo.

Se não encontrou no nó atual:

Se for folha, o valor não existe.

Se não for folha, desce para o filho apropriado e repete.

##  `public void imprimir()`

```java
// Imprime todas as chaves da árvore em ordem crescente.
// Serve para conferir rapidamente o estado da árvore após inserções/remoções.
// Apenas chama a versão recursiva a partir da raiz e quebra a linha no final.
public void imprimir() {
    imprimirRec(raiz);
    System.out.println(); // nova linha para organizar a saída no console
}
```

##  `public void imprimirRec()`
```java
// Percorre a árvore em ORDEM (in-order) e imprime as chaves em ordem crescente.
// Regras da travessia em uma árvore 2-3:
//  - Se o nó tem 1 chave (n == 1): visita filho[0], imprime chaves[0], visita filho[1].
//  - Se o nó tem 2 chaves (n == 2):
//        visita filho[0], imprime chaves[0],
//        visita filho[1], imprime chaves[1],
//        visita filho[2].
//  - Se for folha: apenas imprime as chaves do nó na sequência.
private void imprimirRec(Node23 no) {
    if (no == null) return;

    if (no.folha) {
        // Nó folha: imprime as chaves existentes (1 ou 2)
        for (int i = 0; i < no.n; i++) {
            System.out.print(no.chaves[i] + " ");
        }
    } else {
        // Nó interno:
        if (no.n == 1) {
            // Caso com 1 chave: filho[0], chave[0], filho[1]
            imprimirRec(no.filhos[0]);
            System.out.print(no.chaves[0] + " ");
            imprimirRec(no.filhos[1]);
        } else { // no.n == 2
            // Caso com 2 chaves:
            // filho[0], chave[0], filho[1], chave[1], filho[2]
            imprimirRec(no.filhos[0]);
            System.out.print(no.chaves[0] + " ");
            imprimirRec(no.filhos[1]);
            System.out.print(no.chaves[1] + " ");
            imprimirRec(no.filhos[2]);
        }
    }
}

```
