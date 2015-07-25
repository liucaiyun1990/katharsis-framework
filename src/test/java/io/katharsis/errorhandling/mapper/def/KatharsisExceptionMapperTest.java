package io.katharsis.errorhandling.mapper.def;

import io.katharsis.errorhandling.ErrorData;
import io.katharsis.errorhandling.ErrorResponse;
import io.katharsis.errorhandling.exception.KatharsisMappableException;
import io.katharsis.response.HttpStatus;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

public class KatharsisExceptionMapperTest {

    public static final String TITLE1 = "title1";
    public static final String DETAIL1 = "detail1";

    @Test
    public void shouldMapToErrorResponse() throws Exception {
        KatharsisExceptionMapper mapper = new KatharsisExceptionMapper();
        ErrorResponse response = mapper.toErrorResponse(new SampleKatharsisException());

        assertThat(response.getHttpStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR_500);
        assertThat(response.getData())
                .hasSize(1)
                .extracting("title", "detail")
                .containsExactly(tuple(TITLE1, DETAIL1));
    }

    private static class SampleKatharsisException extends KatharsisMappableException {

        protected SampleKatharsisException() {
            super(HttpStatus.INTERNAL_SERVER_ERROR_500, ErrorData.builder()
                    .setTitle(TITLE1)
                    .setDetail(DETAIL1)
                    .setStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR_500))
                    .build());
        }
    }
}