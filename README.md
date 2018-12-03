
# Projeto PM 2018.2
Projeto final da disciplina de Programação Modular.

## Objetivo
O objetivo deste trabalho é apoiar o trabalho da Comissão de Bolsas do PPGI. O trabalho consiste em gerar uma aplicação Java (obrigatoriamente), sem interface gráfica com usuários (as funções devem ser acessadas por linha de comando), que recebe como entrada arquivos XML referente ao Currículo Lattes dos candidatos e gera a pontuação de cada candidato e o ranking final.

## Regras
Para concorrer uma bolsa, o aluno de mestrado ou doutorado do PPGI deve atender a um conjunto de critérios estabelecidos pelo Colegiado (ou seja, todos os professores permanentes ou colaboradores e os representantes discentes) do PPGI e avaliados pela Comissão de Bolsas.

* Com base nos currículos Lattes dos candidatos, no tipo (mestrado ou doutorado) e número de bolsas disponíveis, a Comissão de Bolsas elabora um ranking dos alunos e apresenta ao Colegiado para referendamento.
* As regras de Concessão de Bolsas podem ser vistas neste link. Para este trabalho são de especial interesse os artigos V, VI e VII.
* Os candidatos devem enviar também um formulário (que pode ser baixado aqui) com a autoavaliação do seu currículo.

Regras que devem ser consideradas, retiradas do documento que descreve os critérios para Concessão de Bolsas:

* Número de semestres cursados sem reprovação no curso de pós-graduação para o qual a bolsa foi pleiteada, conforme o histórico escolar entregue pelo candidato, atribuindo-se um ponto por semestre concluído;
* Prêmios recebidos nos últimos 10 anos referentes a atividades de pesquisa, conforme indicado no currículo entregue pelo candidato, atribuindo-se um ponto por prêmio;
* Artigos completos publicados nos últimos 10 anos em periódicos ou eventos classificados como A1, A2 ou B1 no Qualis da Computação, atribuindo-se 3 pontos por artigo;    
* Artigos completos publicados nos últimos 10 anos em periódicos ou eventos classificados como B2, B3, B4 ou B5 no Qualis da Computação, atribuindo-se 1 ponto por artigo;
* Participação em eventos classificados, atribuindo-se 1 ponto por evento até um máximo de 5 pontos;
* Existência de vínculo com a UNIRIO nos últimos 10 anos, seja pela participação em projetos, bolsas de pesquisa, representação discente ou similar, atribuindo-se um ponto para cada tipo de envolvimento até um máximo de dois pontos.
* Os candidatos devem ser ordenados pelo número de pontos recebidos nos critérios acima, sendo alocados a bolsas de acordo com a ordem decrescente do seu número de pontos e com a disponibilidade de bolsas.

Em caso de empate, o desempate se dará pelos seguintes critérios: número de semestres cursados, publicações em periódicos, publicações em conferências, vínculo com UNIRIO, prêmios, participação em eventos.

## Interface

Não há interface gráfica. A interação com o usuário se dá inteiramente pela linha de comando, por meio da biblioteca [Picocli](https://github.com/remkop/picocli).

É possível rodar o programa com diferentes parâmetros (em qualquer ordem) por linha de comando, de acordo com as opções abaixo:

-   -o <nome-caminho-arquivo-txt-saida\> : indica o caminho do arquivo texto que deve conter a saída do programa.

-   -l <nome-caminho-arquivo-log-errors\> : indica o caminho do arquivo texto que deve conter um log/relatório de erros do programa.

-   -a <nome-caminho-arquivo-xml\> <num-semestres-sem-reprovacao\>: indica os dados do candidato a ser considerado: o caminho do arquivo com o currículo Lattes, no formato XML, e o número de semestres que o aluno já completou sem reprovações. Podem ser passados vários parâmetros "-a" para o programa.

-   -c : gera a saída completa do programa. É equivalente a usar, em conjunto, os parâmetros -pr -ar -anr -pe -vi.

-   -v : modificador que indica que a saída deve ser completa (modo verboso, ou verbose mode).

-   -pr : gera a saída referente aos prêmios recebidos. Para cada candidato, é exibida a quantidade de prêmios considerados e a pontuação obtida. Se o modo verboso estiver ativado, é exibida ao final, para cada candidato, o ano e o nome do prêmios obtidos.

-   -ar : gera a saída referente aos artigos completos no Qualis Restrito (ou seja, publicados em conferências ou periódicos classificados como A1, A2 e B1). Para cada candidato, é exibida a quantidade de artigos considerados e a pontuação obtida. Se o modo verboso estiver ativado, é exibida ao final, para cada candidato: o ano de publicação, nome do artigo, conferência ou periódico associado e o Qualis associado.

-   -anr : gera a saída referente aos artigos completos fora do Qualis Restrito (ou seja, publicados em conferências ou periódicos classificados como B2, B3, B4 e B5). Para cada candidato, é exibida a quantidade de artigos considerados e a pontuação obtida. Se o modo verboso estiver ativado, é exibida ao final, para cada candidato: o ano de publicação, nome do artigo, conferência ou periódico associado e o Qualis associado.

-   -pe : gera a saída referente à participação em eventos classificados (ou seja, referentes a conferências classificadas como A1, A2, B1, B2, B3, B4 e B5). Para cada candidato, é exibida a quantidade de participações consideradas e a pontuação obtida.

## Como utilizar

Dentro da pasta do programa, você encontrará um arquivo .jar. Para executar o programa, faça o seguinte:

- Baixe o projeto para o seu computador;
- Abra um prompt de comando (como cmd ou powershell no Windows);
- Navege até a pasta do projeto, pelo caminho que ela se encontra no seu computador;

Dentro da pasta do projeto, só digitar (trocando <parâmetros> pelos comandos que deseja):

~~~~
java -jar projetopm.jar <parâmetros>
~~~~

## Exemplos 

~~~~
java -jar executavel.jar -o saida.txt -a exemplos/lattes/0398675521406529.xml 2 -a exemplos/lattes/6938694286834426.xml 2

java -jar executavel.jar -o saida_completa.txt -a exemplos/lattes/1920411639358905.xml 1 -a exemplos/lattes/6938694286834426.xml 2 -a exemplos/lattes/luiz_paulo_0239993115999249.xml 0 -v -l saida_log.txt

java -jar executavel.jar -o saida_completa.txt -a exemplos/lattes/1920411639358905.xml 1 -a exemplos/lattes/6938694286834426.xml 2 -a exemplos/lattes/luiz_paulo_0239993115999249.xml 0 -v -l saida_log.txt -c

java -jar executavel.jar -o saida_completa.txt -a exemplos/lattes/0985291000032685.xml 1 -a exemplos/lattes/6938694286834426.xml 2 -a exemplos/lattes/5998143228852295.xml 0 -v -l saida_log.txt -pr 

java -jar executavel.jar -o saida_completa.txt -a exemplos/lattes/0985291000032685.xml 1 -a exemplos/lattes/6938694286834426.xml 2 -a exemplos/lattes/5998143228852295.xml 0 -v -pr -pe 
~~~~

## Observação

Se algum comando acima não funcionar, procurar colocar o caminho absoluto dos arquivos lattes.
