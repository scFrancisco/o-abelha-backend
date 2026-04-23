package com.example.oabelha.Repository;

import com.example.oabelha.Entity.Inscricao;
import com.example.oabelha.Entity.Inscricao.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

    List<Inscricao> findByEventoIdOrderByCriadoEmDesc(Long eventoId);

    List<Inscricao> findByEventoIdAndEstado(Long eventoId, Estado estado);

    Optional<Inscricao> findByReferenciaPagamento(String referencia);

    Optional<Inscricao> findByCodigoConfirmacao(String codigo);

    // Conta lugares ocupados (confirmados + pendentes ainda válidos)
    @Query("SELECT COALESCE(SUM(i.numPessoas), 0) FROM Inscricao i " +
            "WHERE i.evento.id = :eventoId " +
            "AND i.estado IN ('CONFIRMADO', 'PENDENTE') " +
            "AND (i.estado = 'CONFIRMADO' OR i.expiraEm > :agora)")
    int contarLugaresOcupados(Long eventoId, LocalDateTime agora);

    // Busca pendentes expirados para limpeza
    List<Inscricao> findByEstadoAndExpiraEmBefore(Estado estado, LocalDateTime agora);
}