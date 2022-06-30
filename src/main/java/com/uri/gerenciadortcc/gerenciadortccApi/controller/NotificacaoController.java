package com.uri.gerenciadortcc.gerenciadortccApi.controller;

import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.NotificacaoObject;
import com.uri.gerenciadortcc.gerenciadortccApi.controller.objects.TCCObject;
import com.uri.gerenciadortcc.gerenciadortccApi.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/notificacao")
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @PutMapping("/{notificacaoId}/update")
    private String putNotificacao(@PathVariable("notificacaoId")String notificacaoId, @RequestBody NotificacaoObject notificacaoObject) {
        notificacaoService.atualizaNotificacao(Long.valueOf(notificacaoId), notificacaoObject);
        return "Notificacao atualizada com Sucesso";
    }
}
