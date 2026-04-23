package com.example.oabelha.Service;

import com.example.oabelha.Entity.Event;
import com.example.oabelha.Entity.Inscricao;
import com.example.oabelha.Entity.Inscricao.Estado;
import com.example.oabelha.Repository.EventRepository;
import com.example.oabelha.Repository.InscricaoRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class InscricaoService {

    private final InscricaoRepository inscricaoRepository;
    private final EventRepository eventRepository;
    private final JavaMailSender mailSender;

    public InscricaoService(InscricaoRepository inscricaoRepository,
                            EventRepository eventRepository,
                            JavaMailSender mailSender) {
        this.inscricaoRepository = inscricaoRepository;
        this.eventRepository = eventRepository;
        this.mailSender = mailSender;
    }

    public Inscricao criarInscricao(Long eventoId, String nome, String email, int numPessoas) {
        Event evento = eventRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        if (!evento.getInscricoesAtivas()) {
            throw new RuntimeException("Inscrições encerradas para este evento");
        }

        // Verificar capacidade
        if (evento.getCapacidade() != null) {
            int ocupados = inscricaoRepository.contarLugaresOcupados(eventoId, LocalDateTime.now());
            if (ocupados + numPessoas > evento.getCapacidade()) {
                throw new RuntimeException("Não há lugares suficientes disponíveis");
            }
        }

        Inscricao inscricao = new Inscricao();
        inscricao.setEvento(evento);
        inscricao.setNome(nome);
        inscricao.setEmail(email);
        inscricao.setNumPessoas(numPessoas);
        inscricao.setValorTotal(evento.getPreco() * numPessoas);
        inscricao.setReferenciaPagamento(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        inscricao.setExpiraEm(LocalDateTime.now().plusMinutes(30));

        // Se evento for gratuito, confirmar imediatamente
        if (evento.getPreco() == 0.0) {
            inscricao.setEstado(Estado.CONFIRMADO);
            inscricao.setCodigoConfirmacao(UUID.randomUUID().toString().substring(0, 10).toUpperCase());
            inscricao.setPagoEm(LocalDateTime.now());
            inscricao = inscricaoRepository.save(inscricao);
            enviarEmailConfirmacao(inscricao, evento);
        } else {
            inscricao.setEstado(Estado.PENDENTE);
            inscricao = inscricaoRepository.save(inscricao);
            enviarEmailPendente(inscricao, evento);
        }

        return inscricao;
    }

    public Inscricao confirmarPagamento(String referencia) {
        Inscricao inscricao = inscricaoRepository.findByReferenciaPagamento(referencia)
                .orElseThrow(() -> new RuntimeException("Referência não encontrada"));

        if (inscricao.getEstado() == Estado.EXPIRADO) {
            throw new RuntimeException("Esta referência expirou");
        }
        if (inscricao.getEstado() == Estado.CONFIRMADO) {
            throw new RuntimeException("Pagamento já confirmado");
        }

        inscricao.setEstado(Estado.CONFIRMADO);
        inscricao.setPagoEm(LocalDateTime.now());
        inscricao.setCodigoConfirmacao(UUID.randomUUID().toString().substring(0, 10).toUpperCase());
        inscricao = inscricaoRepository.save(inscricao);

        enviarEmailConfirmacao(inscricao, inscricao.getEvento());
        return inscricao;
    }

    public List<Inscricao> listarPorEvento(Long eventoId) {
        return inscricaoRepository.findByEventoIdOrderByCriadoEmDesc(eventoId);
    }

    public int lugaresDisponiveis(Long eventoId) {
        Event evento = eventRepository.findById(eventoId)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
        if (evento.getCapacidade() == null) return 999;
        int ocupados = inscricaoRepository.contarLugaresOcupados(eventoId, LocalDateTime.now());
        return evento.getCapacidade() - ocupados;
    }

    // Expira pendentes a cada 5 minutos
    @Scheduled(fixedRate = 300000)
    public void expirarPendentes() {
        List<Inscricao> expirados = inscricaoRepository
                .findByEstadoAndExpiraEmBefore(Estado.PENDENTE, LocalDateTime.now());
        expirados.forEach(i -> i.setEstado(Estado.EXPIRADO));
        inscricaoRepository.saveAll(expirados);
        if (!expirados.isEmpty()) {
            System.out.println("⏰ " + expirados.size() + " inscrições expiradas");
        }
    }

    private void enviarEmailPendente(Inscricao inscricao, Event evento) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(inscricao.getEmail());
            msg.setSubject("Reserva pendente – " + evento.getTitulo());
            msg.setText(
                    "Olá " + inscricao.getNome() + ",\n\n" +
                            "A tua reserva para \"" + evento.getTitulo() + "\" foi recebida!\n\n" +
                            "Para garantir o teu lugar, efectua o pagamento de " +
                            String.format("%.2f€", inscricao.getValorTotal()) + " via MBWay ou Multibanco.\n\n" +
                            "Referência de pagamento: " + inscricao.getReferenciaPagamento() + "\n\n" +
                            "⚠️ Tens 30 minutos para pagar. Após esse prazo o lugar será libertado.\n\n" +
                            "CRC O Abelha"
            );
            mailSender.send(msg);
        } catch (Exception e) {
            System.err.println("Erro ao enviar email: " + e.getMessage());
        }
    }

    private void enviarEmailConfirmacao(Inscricao inscricao, Event evento) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(inscricao.getEmail());
            msg.setSubject("Inscrição confirmada – " + evento.getTitulo());
            msg.setText(
                    "Olá " + inscricao.getNome() + ",\n\n" +
                            "A tua inscrição em \"" + evento.getTitulo() + "\" está CONFIRMADA! 🎉\n\n" +
                            "Pessoas: " + inscricao.getNumPessoas() + "\n" +
                            "Data: " + evento.getDataEvento() + "\n" +
                            "Local: " + evento.getLocal() + "\n\n" +
                            "O teu código de entrada: " + inscricao.getCodigoConfirmacao() + "\n\n" +
                            "Apresenta este código na entrada do evento.\n\n" +
                            "Até lá!\nCRC O Abelha"
            );
            mailSender.send(msg);
        } catch (Exception e) {
            System.err.println("Erro ao enviar email: " + e.getMessage());
        }
    }
}