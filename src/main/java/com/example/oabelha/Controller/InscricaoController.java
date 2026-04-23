package com.example.oabelha.Controller;

import com.example.oabelha.Entity.Inscricao;
import com.example.oabelha.Service.InscricaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inscricoes")
public class InscricaoController {

    private final InscricaoService inscricaoService;

    public InscricaoController(InscricaoService inscricaoService) {
        this.inscricaoService = inscricaoService;
    }

    // POST /api/inscricoes
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody Map<String, Object> body) {
        try {
            Long eventoId = Long.valueOf(body.get("eventoId").toString());
            String nome = body.get("nome").toString();
            String email = body.get("email").toString();
            int numPessoas = Integer.parseInt(body.get("numPessoas").toString());

            Inscricao inscricao = inscricaoService.criarInscricao(eventoId, nome, email, numPessoas);
            return ResponseEntity.ok(Map.of(
                    "id", inscricao.getId(),
                    "estado", inscricao.getEstado(),
                    "referenciaPagamento", inscricao.getReferenciaPagamento(),
                    "valorTotal", inscricao.getValorTotal(),
                    "expiraEm", inscricao.getExpiraEm(),
                    "codigoConfirmacao", inscricao.getCodigoConfirmacao() != null
                            ? inscricao.getCodigoConfirmacao() : ""
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    // POST /api/inscricoes/confirmar/{referencia}  ← webhook do gateway ou manual
    @PostMapping("/confirmar/{referencia}")
    public ResponseEntity<?> confirmar(@PathVariable String referencia) {
        try {
            Inscricao inscricao = inscricaoService.confirmarPagamento(referencia);
            return ResponseEntity.ok(Map.of(
                    "estado", inscricao.getEstado(),
                    "codigoConfirmacao", inscricao.getCodigoConfirmacao()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    // GET /api/inscricoes/evento/{eventoId}  ← painel admin
    @GetMapping("/evento/{eventoId}")
    public List<Inscricao> listar(@PathVariable Long eventoId) {
        return inscricaoService.listarPorEvento(eventoId);
    }

    // GET /api/inscricoes/evento/{eventoId}/lugares
    @GetMapping("/evento/{eventoId}/lugares")
    public ResponseEntity<?> lugares(@PathVariable Long eventoId) {
        return ResponseEntity.ok(Map.of(
                "disponiveis", inscricaoService.lugaresDisponiveis(eventoId)
        ));
    }
}