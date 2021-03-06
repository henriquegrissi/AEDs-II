import java.io.File;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.text.ParseException;
import java.util.Scanner;
import java.text.SimpleDateFormat;  
import java.util.Date; 

public class Teste {
    public static void main(String args[]) throws Exception {
        try (Scanner sc = new Scanner (System.in,("UTF-8"))) {
            String nomeArq = sc.nextLine();
            Filme filme = new Filme();
            //Atributos para printar a data de acordo com o pub out
            Date d = null;
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
            while (fim(nomeArq)) {
                filme.lerArquivo(nomeArq);
                d = filme.getDataLanc();
                System.out.println(filme.getNome() + " " + filme.getTituloOriginal() + " " + formatter.format(d) + " " + filme.getDuracao() + " " + filme.getGenero() + " " + filme.getIdiomaOriginal()
                + " " + filme.getSitu() + " " + filme.getOrcamento() + " " + filme.getpChave() + "");
                nomeArq = sc.nextLine(); //Recebendo novo nome
            }
        }
      
    }

    public static boolean fim(String r) {
        boolean fim = true;
        if (r.charAt(0) == 'F' && r.charAt(1) == 'I' && r.charAt(2) == 'M') {
            fim = false;
            return fim;
        } else {
            return fim;
        }
    }
}

class Filme {
    private String nome;
    private String tituloOriginal;
    private Date dataLanc; // DateFormat para converter
    private int duracao; // Int em minutos
    private String genero;
    private String idiomaOriginal;
    private String situ;
    private float orcamento; // Float
    private String pChave;

    // Construtores, sests e gets
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTituloOriginal() {
        return tituloOriginal;
    }

    public void setTitulooriginal(String tituloOriginal) {
        this.tituloOriginal = tituloOriginal;
    }

    public Date getDataLanc() {
        return dataLanc;
    }

    // Tranformando pra Date
    public void setDataLanc(Date dataLanc){
       this.dataLanc = dataLanc;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int tempo) {
        this.duracao = tempo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getIdiomaOriginal() {
        return idiomaOriginal;
    }

    public void setIdiomaOriginal(String idiomaOriginal) {
        this.idiomaOriginal = idiomaOriginal;
    }

    public float getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(float valor) {
        
        this.orcamento = valor;     

    }

    public String getSitu() {
        return situ;
    }

    public void setSitu(String situ) {
        this.situ = situ;
    }

    public String getpChave() {
        return pChave;
    }

    public void setpChave(String palavra) {
        this.pChave = palavra;
    }

    public Filme() {
        this.nome = "";
        this.tituloOriginal = "titulo";
        this.dataLanc = null;//Resolver a data
        this.duracao = 0;
        this.genero = "genero";
        this.idiomaOriginal = "";
        this.situ = "";
        this.orcamento = 0;
        this.pChave = "";
    }

    public Filme(String nome, String tituloOriginal, String dataLanc, String duracao, String genero,
    String idiomaOriginal, String orcamento, String situ, String[] pChave) {

    }

    // M??todo para remover Tag
    public String removeTag(String line) {
        String resp = " ";
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '<') {
                i++;
            while (line.charAt(i) != '>')
                i++;
            } else {
            resp += line.charAt(i);
            }
        }
    // Deletando para n??o sair no pub.out
        resp = resp.replaceAll("T??tulo original", "");
        resp = resp.replaceAll("Idioma original", "");
        resp = resp.replaceAll("Or??amento", "");
        resp = resp.replaceAll("\\$", "");
        resp = resp.replaceAll("Situa????o", "");
        resp = resp.replaceAll("(BR)", "");
        resp = resp.replaceAll("(US)", ""); // m??todo mais f??cil de eliminar isso
        resp = resp.replaceAll("(AU)", "");
        resp = resp.replaceAll("(JP)", "");
        resp = resp.replaceAll("(IT)", "");
        resp = resp.replaceAll("(GB)", "");
        resp = resp.replaceAll("&nbsp;", "");
        resp = resp.replaceAll("&amp;", "");
        resp = resp.replaceAll("\\(", "");
        resp = resp.replaceAll("\\)", "");
        return resp;
    }

    // M??todo para ler o arquivo
    public void lerArquivo(String arquivo) throws Exception {

        // Caminho passado como parametro
        /*Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current absolute path is: " + s);*/
        
        //                          !!!!!!!!!!!!!!!!!!!!!!!!!!!Adicionar /tmp/filmes!!!!!!!!!!!!!!!!!!!!!!!!!
        File file = new File("/tmp/filmes/" + arquivo);

        Scanner scanner = new Scanner(file, "UTF-8");
        String line = scanner.nextLine();
        boolean possuiTO = true; //V??riavel de controle para o titulo original
        while (scanner.hasNextLine()) {
            // NOME
            if (line.contains("h2 class")) {
                line = scanner.nextLine();
                this.setNome(removeTag(line).trim());
                possuiTO = false; //Sempre que receber um novo nome seta a vari??vel para falsa
            }
            // TITULO ORIGINAL
            if (line.contains("<p class=\"wrap\">")) {
                //line = scanner.nextLine();
                possuiTO = true; //Se entrar neste IF ?? pq tem t??tulo original
                this.setTitulooriginal(removeTag(line).trim());
            }

            //Condi????o para deletar o antigo t??tulo original (caso n??o tenha um)
            if(possuiTO == false){
                tituloOriginal = getNome();
                setTitulooriginal(tituloOriginal);
            }

            // DATA DE LAN??AMENTO
            if (line.contains("span class=\"release\"")) {
                line = scanner.nextLine();
                Date data = null;
                line = removeTag(line).trim();
                try {
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
                    data = formatter.parse(line); 
                } catch (Exception e) {
                    e.printStackTrace();
                }  
                    this.setDataLanc(data);
            }

            // DURA????O (PRECISA CONVERTER PRA MINUTOS)
            if (line.contains("class=\"runtime\"")) {
                line = scanner.nextLine();
                line = scanner.nextLine();
                int tempo = 0, horas = 0;
                line = removeTag(line).trim();
                
                //Try para caso n??o tenha pelo menos uma dezena nos minutos 
                try{
                    tempo = Integer.parseInt(line.substring(line.indexOf('m') - 2, line.indexOf('m'))); // "Testa" se tem 2 n??meoros
                }catch(NumberFormatException | StringIndexOutOfBoundsException e){
                    tempo = Integer.parseInt(line.substring(line.indexOf('m') - 1, line.indexOf('m')));
                }
                // char 'h' existe?
                if (line.indexOf('h') != -1){   // retorna -1 quando falso
                    horas = Integer.parseInt(line.substring(0, line.indexOf('h')));
                    //Converte horas em minutos
                    horas = horas * 60;
                    tempo += horas; //Minutos totais
                } 
                this.setDuracao(tempo);
            }

            // GEN??RO
            if (line.contains("class=\"genres\"")) {
                line = scanner.nextLine();
                line = scanner.nextLine();
                this.setGenero(removeTag(line).trim());
                line = scanner.nextLine();

            }

            // IDIOMA ORIGINAL
            if (line.contains("Idioma original")) {

                this.setIdiomaOriginal(removeTag(line).trim());
                line = scanner.nextLine();
            }

            // Or??amento
            if (line.contains("Or??amento")) {
                float valor = 0;
                line = line.replaceAll(",", "");
                line = removeTag(line).trim();
                try{
                    valor = Float.parseFloat(line); //String pra float
                }catch(NumberFormatException e){
                    valor = 0; //Trata e a excess??o caso n??o haja um vlaor no or??amento
                }
                this.setOrcamento(valor);
                line = scanner.nextLine();
            }

            // Situa????o
            if (line.contains("Situa????o")) {
                //Ler a linha
                this.setSitu(removeTag(line).trim());
                line = scanner.nextLine();
            }

            // Palavras-Chave
            if (line.contains("<h4><bdi>Palavras-chave</bdi></h4>")){
                String [] palavras = new String [50]; 
                String palavra = "";
                int cont = 0;
                //Ler todas essas linhas t?? chegar na condi????o do while
                line = scanner.nextLine();
                line = scanner.nextLine();
                line = scanner.nextLine();
                line = scanner.nextLine();
                while(line.contains("<li>")){
                    line = removeTag(line).trim();
                    palavras [cont] = line;
                    line = scanner.nextLine();
                    line = scanner.nextLine();
                    cont++;
                }
                //Transformando o array
                for (int i = 0; i < cont; i++){
                    if (i == 0){
                        palavra = palavras[i];
                    }else{
                        palavra =  palavra + ", " + palavras[i];
                    }
                }
                palavra = '[' + palavra + ']';
                this.setpChave(palavra);
            }
                       
            line = scanner.nextLine();
        }
        scanner.close();
    }
}
