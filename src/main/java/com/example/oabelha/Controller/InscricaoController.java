package com.example.oabelha.Controller;

import com.example.oabelha.Entity.Inscricao;
import com.example.oabelha.Service.InscricaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            String telefone = body.getOrDefault("telefone", "").toString();
            String metodoPagamento = body.getOrDefault("metodoPagamento", "").toString();
            int numPessoas = Integer.parseInt(body.get("numPessoas").toString());

            Inscricao i = inscricaoService.criarInscricao(eventoId, nome, email, telefone, metodoPagamento, numPessoas);

            Map<String, Object> resp = new HashMap<>();
            resp.put("id", i.getId());
            resp.put("estado", i.getEstado());
            resp.put("referenciaPagamento", i.getReferenciaPagamento());
            resp.put("valorTotal", i.getValorTotal());
            resp.put("expiraEm", i.getExpiraEm());
            resp.put("codigoConfirmacao", i.getCodigoConfirmacao() != null ? i.getCodigoConfirmacao() : "");
            resp.put("eventoTitulo", i.getEvento().getTitulo());
            resp.put("eventoData", i.getEvento().getDataEvento());
            resp.put("eventoLocal", i.getEvento().getLocal());
            return ResponseEntity.ok(resp);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    // GET /api/inscricoes  ← painel admin
    @GetMapping
    public ResponseEntity<?> listarTodas() {
        List<Map<String, Object>> resultado = inscricaoService.listarTodas().stream()
                .map(i -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("id", i.getId());
                    m.put("nome", i.getNome());
                    m.put("email", i.getEmail());
                    m.put("telefone", i.getTelefone() != null ? i.getTelefone() : "");
                    m.put("numPessoas", i.getNumPessoas());
                    m.put("valorTotal", i.getValorTotal());
                    m.put("metodoPagamento", i.getMetodoPagamento() != null ? i.getMetodoPagamento() : "");
                    m.put("estado", i.getEstado());
                    m.put("referenciaPagamento", i.getReferenciaPagamento());
                    m.put("createdAt", i.getCreatedAt());

                    Map<String, Object> evento = new HashMap<>();
                    evento.put("titulo", i.getEvento().getTitulo());
                    evento.put("dataEvento", i.getEvento().getDataEvento());
                    m.put("evento", evento);
                    return m;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultado);
    }

    // PATCH /api/inscricoes/{id}/status  ← admin aceita ou rejeita
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(@PathVariable Long id,
                                              @RequestBody Map<String, String> body) {
        try {
            Inscricao inscricao = inscricaoService.atualizarStatus(id, body.get("status"));
            return ResponseEntity.ok(Map.of("estado", inscricao.getEstado()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    // GET /api/inscricoes/evento/{eventoId}/lugares
    @GetMapping("/evento/{eventoId}/lugares")
    public ResponseEntity<?> lugares(@PathVariable Long eventoId) {
        Map<String, Object> resp = new HashMap<>();
        resp.put("disponiveis", inscricaoService.lugaresDisponiveis(eventoId));
        return ResponseEntity.ok(resp);
    }

    // POST /api/inscricoes/confirmar/{referencia}  ← webhook manual
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
}
