package com.example.proyecto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FilmDataSource {
    private static final String FILE_NAME = "pelicila.txt";
    static File ruta;
    public static ArrayList<Film> films;
    public static void Initialize() {

        films=new ArrayList<>();


        /*
        films.add(new Film(1,R.drawable.endgamee,"End Game","Anthony y Joe Russo",2019,0,2,"https://www.imdb.com/title/tt4154796/?ref_=hm_tpks_tt_i_6_pd_tp1_pbr_ic","Los vengadores viajan en el tiempo para recuperar las gemas del infinito y poder derrotar a Thanos"));//
        films.add(new Film(2,R.drawable.inter,"Interstellar","Christopher Nolan",2014,3,2,"http://www.imdb.com/title/tt0816692","Un equipo de exploradores viaja a través de un agujero de gusano en el espacio en un intento por asegurar la supervivencia de la humanidad."));
        films.add(new Film(3,R.drawable.regresoalfuturo,"Back to the future","Robert Zemeckis",1985,3,2,"http://www.imdb.com/title/tt0088763","Marty McFly, es enviado 30 años al pasado en un viaje en el tiempo en el DeLorean"));
        films.add(new Film(4,R.drawable.oppenheimer,"Oppenheimer","Christopher Nolan",2023,2,2,"https://www.imdb.com/title/tt15398776/?ref_=hm_tpks_tt_i_3_pd_tp1_pbr_ic","Oppenheimer es una pelicula biográfica de suspenso británicoestadounidense de 2023, escrita y dirigida por Christopher Nolan. Basada en American Prometheus, una biografía de 2005 escrita por Kai Bird y Martin J."));
        films.add(new Film(R.drawable.joker,"Joker","Todd Phillips",2019,2,1,"httºps://www.imdb.com/title/tt7286456/?ref_=hm_tpks_tt_i_4_pd_tp1_pbr_ic","Joker es una película de suspense psicológico estadounidense de 2019 dirigida y producida por Todd Phillips, quien coescribió el guion con Scott Silver. La película, basada en personajes de DC Comics, está protagonizada por Joaquin Phoenix como El Joker y proporciona una historia de su origen alternativa para el personaje. "));
        films.add(new Film(R.drawable.ellobowall,"El lobo de Wall Street","Martin Scorsese",2014,1,2,"https://www.imdb.com/title/tt0993846/?ref_=hm_tpks_tt_i_4_pd_tp1_pbr_ic","El lobo de Wall Street —cuyo título original en inglés es The Wolf of Wall Street— es una película estadounidense de comedia negra criminal biográfica de 2013 dirigida por Martin Scorsese y escrita por Terence Winter, basada en las memorias de Jordan Belfort del mismo nombre de 2007."));
        films.add(new Film(R.drawable.gladiator,"Gladiator","Ridley Scott",2000,0,0,"https://www.imdb.com/title/tt0172495/?ref_=nv_sr_srsg_0_tt_8_nm_0_q_gladiator","El general romano Máximo es el soporte más leal del emperador Marco Aurelio, que lo ha conducido de victoria en victoria. Sin embargo, Cómodo, el hijo de Marco Aurelio, está celoso del prestigio de Máximo y aún más del amor que su padre siente por él."));
        films.add(new Film(R.drawable.a1,"El Señor de los Anillos: la Comunidad del Anillo","Peter Jackson" ,2001,0,1,"https://www.imdb.com/title/tt0120737/?ref_=nv_sr_srsg_6_tt_7_nm_1_q_el%2520se%25C3%25B1or%2520de%2520los%2520a","En la Tierra Media, el Señor Oscuro Sauron forjó los Grandes Anillos del Poder y creó uno con el poder de esclavizar a toda la Tierra Media. Frodo Bolsón es un hobbit al que su tío Bilbo hace portador del poderoso Anillo Único con la misión de destruirlo."));
        films.add(new Film(R.drawable.aninyos,"El señor de los anillos : Las dos torres","Peter Jackson",2002,0,1,"https://www.imdb.com/title/tt0167261/?ref_=hm_tpks_tt_i_4_pd_tp1_pbr_ic","Los hobbits Frodo y Sam descubren que Gollum les está siguiendo. La criatura les promete guiarlos hasta las puertas de Mordor si después lo dejan libre. Aragorn, el elfo Legolas y Gimli el enano llegan al reino de Rohan y descubren que el rey Theoden está bajo el conjuro de Sarumán."));
        films.add(new Film(R.drawable.retorno,"El Señor de los Anillos: el retorno del Rey" ,"Peter Jackson",2003,0,1,"https://www.imdb.com/title/tt0120737/?ref_=nv_sr_srsg_6_tt_7_nm_1_q_el%2520se%25C3%25B1or%2520de%2520los%2520a","La batalla por la Tierra Media ha empezado. Las fuerzas de Saruman han sido destruidas y por primera vez parece que hay una pequeña esperanza. Sin embargo, el poder de Sauron crece y su interés se centra en Gondor, el último reducto de los hombres, cuyo heredero es Aragorn."));
        films.add(new Film(R.drawable.trans,"Transformers: el despertar de las bestias","Steven Caple Jr.",2023,0,2,"https://www.imdb.com/title/tt5090568/?ref_=nv_sr_srsg_0_tt_8_nm_0_q_trans","Durante la década de 1990, los Maximals, Predacons y Terrorcons se unen a la batalla existente en la Tierra entre Autobots y Decepticons."));
        films.add(new Film(R.drawable.infinity,"Avengers: Infinity War","Anthony RussoJoe Russo",2018,0,2,"https://www.imdb.com/title/tt4154756/?ref_=nv_sr_srsg_0_tt_8_nm_0_q_Avengers%253A%2520Infinity%2520War","El poderoso Thanos, decidido a hacerse con las Gemas del Infinito para controlar el universo, ataca la nave en la que huyen los supervivientes de Asgard. Su intención es hacerse con la segunda de las gemas, ahora en manos de Loki."));
        films.add(new Film(R.drawable.hobit1,"El hobbit: Un viaje inesperado","Peter Jackson",2012,2,2,"https://www.imdb.com/title/tt0903624/?ref_=nv_sr_srsg_0_tt_8_nm_0_q_el%2520hobb","Un reticente hobbit, Bilbo Bolsón, parte hacia la Montaña Solitaria con un animado grupo de enanos para reclamar su hogar en la montaña, y el oro que contiene, al dragón Smaug."));
        films.add(new Film(R.drawable.ho2,"El hobbit: La desolación de Smaug","Peter Jackson",2014,2,2,"https://www.imdb.com/title/tt1170358/?ref_=nv_sr_srsg_0_tt_5_nm_3_q_El%2520hobbit%2520l","Los enanos, junto con Bilbo Bolsón y Gandalf el Gris, continúan en su misión para reclamar Erebor, su hogar, de Smaug. Bilbo Bolsón posee un misterioso y mágico anillo."));
        films.add(new Film(R.drawable.h3,"El hobbit: La batalla de los cinco ejércitos","Peter Jackson",2014,2,2,"https://www.imdb.com/title/tt2310332/?ref_=nv_sr_srsg_6_tt_6_nm_2_q_hobbit","Bilbo y compañía se ven obligados a entrar en guerra contra una serie de combatientes y evitar que la Montaña Solitaria caiga en manos de una creciente oscuridad."));
        films.add(new Film(R.drawable.pira,"Piratas del Caribe: En mareas misteriosas","Rob Marshall",2011,0,2,"https://www.imdb.com/title/tt1298650/?ref_=hm_tpks_tt_i_20_pd_tp1_pbr_ic","Jack Sparrow y Barbossa se embarcan en una búsqueda para encontrar la escurridiza fuente de la juventud, solo para descubrir que Barbanegra y su hija también están tras ella."));
        films.add(new Film(R.drawable.corr,"El corredor del laberinto","Wes Ball",2014,0,2,"https://www.imdb.com/title/tt1790864/?ref_=hm_tpks_tt_i_16_pd_tp1_pbr_ic","Thomas es depositado en una comunidad de chicos después de que se le borre la memoria, aprendiendo pronto que todos están atrapados en un laberinto que le exigirá unir fuerzas con sus compañeros corredores para poder escapar."));
        films.add(new Film(R.drawable.cor2,"El corredor del laberinto : Las pruebas ","Wes Ball",2015,0,0,"https://www.imdb.com/title/tt4046784/?ref_=nv_sr_srsg_3_tt_4_nm_0_q_corredor%2520del%2520laberinto","Tras haber escapado del laberinto, los habitantes del área se enfrentan ahora a una nueva serie de retos en los caminos de un paisaje desolado lleno de obstáculos inimaginables."));
        films.add(new Film(R.drawable.co3,"El corredor del laberinto : La cura mortal","Wes Ball",2018,0,0,"https://www.imdb.com/title/tt4500922/?ref_=nv_sr_srsg_7_tt_8_nm_0_q_El%2520corredor%2520del%2520laberinto","Thomas, un heroico joven, emprende una misión para encontrar una cura para una enfermedad mortal conocida como La llamarada."));
        films.add(new Film(R.drawable.torr,"Torrente 5: Operación Eurovegas","Santiago Segura",2014,0,2,"https://www.imdb.com/title/tt3321428/?ref_=nv_sr_srsg_6_tt_7_nm_1_q_torrente","El policía corrupto Torrente sale de la cárcel en el año 2018 para encontrar una España diferente a la que conocía."));
        films.add(new Film(R.drawable.tres300,"300","Zack Snyder",2006,0,2,"https://www.imdb.com/title/tt0416449/?ref_=nv_sr_srsg_0_tt_8_nm_0_q_300","El rey Leónidas de Esparta y una fuerza de 300 hombres luchan contra los persas en las Termópilas en el año 480 a. C."));
        */
    }

    public static void InicializeFromFile(File fichero){
        ruta = new File(fichero, FILE_NAME);
        ArrayList<Film> aux = null;

        try(FileInputStream fis = new FileInputStream(ruta);
            ObjectInputStream leer = new ObjectInputStream(fis)){

            aux = (ArrayList<Film>)leer.readObject();

        }catch (IOException | ClassNotFoundException e){
            // Nada
        }

        if(aux == null){
            Initialize();
        }else {
            films = aux;
        }
    }

    public static void SaveFilms(){
        if(ruta == null){
            return;
        }

        try(FileOutputStream fos = new FileOutputStream(ruta);
            ObjectOutputStream escribir = new ObjectOutputStream(fos)){

            escribir.writeObject(films);

        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
