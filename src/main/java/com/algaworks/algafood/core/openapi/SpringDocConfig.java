package com.algaworks.algafood.core.openapi;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v1.openapi.model.CollectionModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.LinksModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PageModelOpenApi;
import com.algaworks.algafood.api.v1.openapi.model.PageableModelOpenApi;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Links;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Type;

import static org.springdoc.core.utils.SpringDocUtils.getConfig;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration
public class SpringDocConfig {
    private static final String MSG_REQUISICAO_INVALIDA = "Requisição inválida (erro do cliente).";
    private static final String MSG_ERRO_INTERNO_SERVIDOR = "Erro interno do servidor";
    private static final String MSG_RECURSO_NAO_POSSUI_REPRESENTACAO = "Recurso não possui representação que " +
            "poderia ser aceita pelo consumidor";

    static {
        getConfig()
                .replaceParameterObjectWithClass(Pageable.class, PageableModelOpenApi.class)
                .replaceParameterObjectWithClass(PageRequest.class, PageableModelOpenApi.class)
                .replaceWithClass(Page.class, PageModelOpenApi.class)
                .replaceWithClass(Links.class, LinksModelOpenApi.class);
    }

    @Bean
    public GroupedOpenApi groupedOpenApiV1() {
        return GroupedOpenApi.builder()
                .group("V1")
                .pathsToMatch("/v1/**")
                .addOpenApiCustomizer(addSchema(PageableModelOpenApi.class))
                .addOpenApiCustomizer(addSchema(Problem.class))
                .addOpenApiCustomizer(addSchema(Problem.Object.class))
                .addOpenApiCustomizer(addSchema(PageModelOpenApi.class))
                .addOpenApiCustomizer(addSchema(CollectionModelOpenApi.class))
                .addOpenApiCustomizer(globalGetResponseMessages())
                .addOpenApiCustomizer(globalPostPutResponseMessages())
                .addOpenApiCustomizer(globalDeleteResponseMessages())
                .build();
    }

    @Bean
    public GroupedOpenApi groupedOpenApiV2() {
        return GroupedOpenApi.builder()
                .group("V2")
                .pathsToMatch("/v2/**")
                .addOpenApiCustomizer(addSchema(PageableModelOpenApi.class))
                .addOpenApiCustomizer(addSchema(Problem.class))
                .addOpenApiCustomizer(addSchema(Problem.Object.class))
                .addOpenApiCustomizer(addSchema(CollectionModelOpenApi.class))
                .addOpenApiCustomizer(globalGetResponseMessages())
                .addOpenApiCustomizer(globalPostPutResponseMessages())
                .addOpenApiCustomizer(globalDeleteResponseMessages())
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info().title("Algafood API")
                        .description("API aberta para clientes e restaurantes")
                        .version("v1"));

    }

    private OpenApiCustomizer addSchema(Type tipo) {
        return openApi -> openApi.getComponents().getSchemas().putAll(ModelConverters.getInstance().read(tipo));
    }

    private OpenApiCustomizer globalGetResponseMessages() {
        return openApi -> openApi.getPaths().values().forEach(pathItem ->
                pathItem.readOperationsMap().computeIfPresent(PathItem.HttpMethod.GET, (httpMethod, operation) -> {
                    ApiResponses apiResponses = operation.getResponses();

                    apiResponses.addApiResponse(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()),
                            createApiResponse(MSG_RECURSO_NAO_POSSUI_REPRESENTACAO, null));
                    apiResponses.addApiResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                            createApiResponse(MSG_ERRO_INTERNO_SERVIDOR, createProblemaSchema()));

                    return operation;
                }));
    }

    private OpenApiCustomizer globalPostPutResponseMessages() {
        return openApi -> openApi.getPaths().values().forEach(pathItem -> {
            ApiResponses apiResponses = null;

            if (pathItem.getPost() != null) {
                apiResponses = pathItem.getPost().getResponses();
            }

            if (pathItem.getPut() != null) {
                apiResponses = pathItem.getPut().getResponses();
            }

            if (apiResponses != null) {
                apiResponses.addApiResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                        createApiResponse(MSG_REQUISICAO_INVALIDA, createProblemaSchema()));
                apiResponses.addApiResponse(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()),
                        createApiResponse(MSG_RECURSO_NAO_POSSUI_REPRESENTACAO, null));
                apiResponses.addApiResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                        createApiResponse(MSG_ERRO_INTERNO_SERVIDOR, createProblemaSchema()));
                apiResponses.addApiResponse(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()),
                        createApiResponse("Requisição recusada porque o corpo " +
                                "está em um formato não suportado.", createProblemaSchema()));
            }
        });
    }

    private OpenApiCustomizer globalDeleteResponseMessages() {
        return openApi -> openApi.getPaths().values().forEach(pathItem ->
                pathItem.readOperationsMap().computeIfPresent(PathItem.HttpMethod.DELETE, (httpMethod, operation) -> {
                    ApiResponses apiResponses = operation.getResponses();

                    apiResponses.addApiResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                            createApiResponse(MSG_REQUISICAO_INVALIDA, createProblemaSchema()));
                    apiResponses.addApiResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                            createApiResponse(MSG_ERRO_INTERNO_SERVIDOR, createProblemaSchema()));

                    return operation;
                }));
    }

    private ApiResponse createApiResponse(String description, Schema<?> schema) {
        return new ApiResponse().description(description)
                .content(new Content().addMediaType(APPLICATION_JSON_VALUE, new MediaType().schema(schema)));
    }

    private Schema<Object> createProblemaSchema(){
        var problemaSchema = new Schema<>();
        problemaSchema.setName("Problema");
        problemaSchema.set$ref("#/components/schemas/Problema");
        return problemaSchema;
    }
}
