package com.example.oabelha;

import com.example.oabelha.Entity.Event;
import com.example.oabelha.Repository.EventRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class EventDataSeeder implements CommandLineRunner {

    private final EventRepository eventRepository;

    public EventDataSeeder(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void run(String... args) {
        eventRepository.deleteAll();

        List<Event> eventos = List.of(

                createEvento(
                        "Festa Popular do Abelha",
                        "Uma noite de música, convívio e boa disposição para toda a comunidade.",
                        "<h2>Uma Noite Inesquecível</h2><p>A Festa Popular do CRC O Abelha é um dos momentos mais aguardados do ano. Com música ao vivo, comes e bebes e muita animação, este evento reúne sócios, famílias e amigos numa celebração aberta a toda a comunidade.</p><h2>Programa</h2><p>A festa começa às 18h com animação para os mais novos, seguindo-se um jantar convívio a partir das 20h. A noite prolonga-se com atuações musicais até às 2h.</p><p><strong>Entrada livre para todos os sócios.</strong> Não-sócios: 5€.</p>",
                        LocalDateTime.of(2025, 6, 20, 18, 0),
                        "Sede do CRC O Abelha",
                        "/public/noticias/image.png",
                        100,
                        5.0
                ),

                createEvento(
                        "Torneio de Futebol Inter-Freguesias",
                        "Torneio amigável com equipas convidadas, promovendo o desporto e o convívio.",
                        "<h2>Desporto e Convívio</h2><p>O Torneio de Futebol Inter-Freguesias é uma competição amigável que junta equipas de várias freguesias da região. O objetivo é promover o espírito desportivo, a camaradagem e o gosto pelo futebol.</p><h2>Formato</h2><p>O torneio decorre em formato de grupos, com jogos de 30 minutos. As duas melhores equipas de cada grupo avançam para a fase a eliminar.</p><p><strong>Inscrições abertas até 1 de Julho.</strong> Contacte a sede para mais informações.</p>",
                        LocalDateTime.of(2025, 7, 12, 9, 0),
                        "Campo Desportivo Municipal",
                        "/public/noticias/image.png",
                        200,
                        0.0
                ),

                createEvento(
                        "Jantar de Associados 2025",
                        "Um momento especial de confraternização entre sócios, dirigentes e amigos do clube.",
                        "<h2>A Nossa Grande Noite</h2><p>O Jantar de Associados é a celebração anual do CRC O Abelha, um momento de reconhecimento e gratidão a todos os que fazem parte desta grande família.</p><h2>Destaques da Noite</h2><p>Nesta edição contamos com um menu especial preparado pelos nossos parceiros locais, entrega de distinções a sócios de longa data, e um momento musical surpresa.</p><p><strong>Reservas obrigatórias</strong> até 15 de Agosto. Lugares limitados.</p>",
                        LocalDateTime.of(2025, 8, 30, 20, 0),
                        "Salão Nobre da Sede",
                        "/public/noticias/image.png",
                        80,
                        25.0
                ),

                createEvento(
                        "Workshop de Desportos de Verão",
                        "Aprende novas modalidades desportivas com instrutores especializados.",
                        "<h2>Mexe-te este Verão</h2><p>O CRC O Abelha organiza um workshop intensivo de desportos de verão, aberto a todas as idades. Desde ténis de mesa a badminton, passando por jogos tradicionais portugueses.</p><h2>Modalidades</h2><p>Ténis de mesa, Badminton, Xadrez, Jogos tradicionais. Cada sessão tem a duração de 2 horas com monitor certificado.</p><p><strong>Inscrição gratuita</strong> para sócios. Vagas limitadas a 20 participantes por sessão.</p>",
                        LocalDateTime.of(2025, 7, 26, 10, 0),
                        "Pavilhão Desportivo",
                        "/public/noticias/image.png",
                        20,
                        0.0
                ),

                createEvento(
                        "Convívio de Natal",
                        "Celebra a época natalícia com toda a família do CRC O Abelha.",
                        "<h2>Natal em Família</h2><p>O Convívio de Natal é uma tradição do clube que reúne gerações à volta de uma mesa. Com troca de prendas, atuações dos mais novos e um jantar de Natal completo.</p><h2>Programa</h2><p>Receção às 19h, jantar às 20h, atuação do grupo de teatro juvenil às 21h30, e Pai Natal para as crianças às 22h.</p><p><strong>Evento exclusivo para sócios e familiares.</strong></p>",
                        LocalDateTime.of(2025, 12, 20, 19, 0),
                        "Sede do CRC O Abelha",
                        "/public/noticias/image.png",
                        150,
                        15.0
                )
        );

        eventRepository.saveAll(eventos);
        System.out.println("✅ Eventos populados com sucesso!");
    }

    private Event createEvento(String titulo, String descricao, String corpo,
                               LocalDateTime data, String local, String imagem,
                               Integer capacidade, Double preco) {
        Event e = new Event();
        e.setTitulo(titulo);
        e.setDescricao(descricao);
        e.setCorpoEvento(corpo);
        e.setDataEvento(data);
        e.setLocal(local);
        e.setImagemCapa(imagem);
        e.setCapacidade(capacidade);
        e.setPreco(preco);
        e.setInscricoesAtivas(true);
        return e;
    }
}