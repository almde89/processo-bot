# Como executar a lib
Adicionar o `.jar` no classpath do seu projeto. Para construir o projeto, basta executar:

`$ ./gradlew clean build`

Após isso, basta obter o `.jar` em build/libs 

## build.gradle
```groovy
apply plugin: 'java'

repositories {
    // Use 'jcenter' for resolving your dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
    mavenCentral()
}

subprojects {
    repositories {
        // Use 'jcenter' for resolving your dependencies.
        // You can declare any Maven/Ivy/file repository here.
        jcenter()
        mavenCentral()
    }
}

dependencies {
    // The production code uses the SLF4J logging API at compile time
    compile 'org.glassfish.jersey.containers:jersey-container-jdk-http:2.26-b03'
    compile project(':service')

    // Declare the dependency for your favourite test framework you want to use in your tests.
    // TestNG is also supported by the Gradle Test task. Just change the
    // testCompile dependency to testCompile 'org.testng:testng:6.8.1' and add
    // 'test.useTestNG()' to your build script.
    testCompile 'junit:junit:4.12'
}
```

## Classe que levanta o servidor
BotService.kt
```kotlin
package br.jus.trf1.bot.service

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory
import org.glassfish.jersey.server.ResourceConfig
import javax.ws.rs.core.UriBuilder


fun main(args: Array<String>) {
    val baseUri = UriBuilder.fromUri("http://localhost/").port(8080).build()
    val config = ResourceConfig(BotService::class.java)
    val server = JdkHttpServerFactory.createHttpServer(baseUri, config)
}
```

## Classe de Serviço
BotService.java
```java
package br.jus.trf1.bot.service;

import br.jus.trf1.bot.NovidadeProcessoBot;
import br.jus.trf1.bot.Requisicao;
import br.jus.trf1.bot.RequisicaoRepository;
import br.jus.trf1.bot.novidade.Atualizacao;
import br.jus.trf1.bot.novidade.Novidade;
import br.jus.trf1.telegram.bot.SendMessageFactoryImpl;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.response.GetUpdatesResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Path("botService")
public class BotService {

    public BotService () throws IOException {
        properties = new Properties();
        properties.load(new FileInputStream("offset.properties"));
    }

    @GET
    @Produces("text/plain")
    public Response getMovieEvent() throws IOException {
        final TelegramBot telegramBot = TelegramBotAdapter.build("467673236:AAGON7ozkP_e3kvja-f_0CcdpL1-8rQmiLs");
        final NovidadeProcessoBot bot = new NovidadeProcessoBot();
        final List<Update> updates;
        final ArrayList<Requisicao> requisicoesAtualizacao = new ArrayList<>();
        Integer offset = 0;
        bot.setSendMessageFactory(new SendMessageFactoryImpl());
        bot.setAtualizacaoService(processo -> {
            final StringBuilder cabecalhoBuilder = new StringBuilder();
            final StringBuilder novidadeBuilder = new StringBuilder();
            final ArrayList<Atualizacao> atualizacoes = new ArrayList<>();
            cabecalhoBuilder.append("Processo: ").append("<b>").append(processo).append("</b>").append("\n")
            .append("Data de autuação: ").append("<b>").append("05/01/2017").append("</b>").append("\n")
            .append("As seguintes movimentações ocorreram:").append("\n");
            novidadeBuilder.append("Movimentação tal - outras informações");

            atualizacoes.add(new Atualizacao.Builder()
                    .adicionarCabecalho(cabecalhoBuilder.toString()).finalizar()
                    .adicionar(new Novidade.Builder().atualizacao(novidadeBuilder.toString()).build())
                    .adicionar(new Novidade.Builder().atualizacao(novidadeBuilder.toString()).build())
                    .adicionar(new Novidade.Builder().atualizacao(novidadeBuilder.toString()).build())
                    .build());
            return atualizacoes;
        });
        bot.setTelegramBot(telegramBot);

        updates = obterMensagens(telegramBot);
        updates.forEach(update -> {
            if (update.editedMessage() == null) {
                requisicoesAtualizacao.add(new Requisicao.Builder().processo("1000438-56.2017.4.01.3400")
                        .chatId(update.message().chat().id()).build());
                requisicoesAtualizacao.add(new Requisicao.Builder().processo("1020631-67.2017.4.01.3401")
                        .chatId(update.message().chat().id()).build());
                bot.setRequisicaoAtualizacaoRepository(new RequisicaoRepository() {
                    @Override
                    public List<Requisicao> obter(Long chatId) {
                        return requisicoesAtualizacao;
                    }

                    @Override
                    public Requisicao registrar(Requisicao requisicao) {
                        return new Requisicao.Builder().message(update.message()).build();
                    }

                    @Override
                    public Requisicao obter(String s, Long aLong) {
                        return requisicoesAtualizacao.stream().filter(requisicao -> requisicao.getChatId().equals(aLong)
                                && requisicao.getProcesso().equals(s)).findFirst().get();
                    }

                });
                bot.responder(new Requisicao.Builder().message(update.message())
                        .sendMessageFactory(new SendMessageFactoryImpl())
                        .telegramBot(telegramBot)
                        .build());
                requisicoesAtualizacao.clear();
            }
        });

        offset = updates.get(updates.size() -1).updateId() + 1;
        properties.setProperty("offset", offset.toString());
        properties.store(new FileOutputStream("offset.properties"), "");

        return Response.ok("Get updates finalizado").build();
    }

    private List<Update> obterMensagens(final TelegramBot bot) {
        final GetUpdates getUpdates = new GetUpdates().limit(100).offset(Integer.parseInt(properties.getProperty("offset")))
                .timeout(10000);

        final GetUpdatesResponse updatesResponse = bot.execute(getUpdates);
        return updatesResponse.updates();
    }

    private Properties properties;
}
```
> Criar um offset.properties no root do projeto.
