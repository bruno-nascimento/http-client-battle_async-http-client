package br.com.labbs.workout.httpclientbattle;

import br.com.labbs.workout.httpclientbattle.shared.HttpClient;
import org.asynchttpclient.BoundRequestBuilder;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Response;

import java.util.concurrent.ExecutionException;

@SuppressWarnings("unused")
public class AsyncHttpClient implements HttpClient<BoundRequestBuilder, Response, AsyncHttpClient> {

    private final org.asynchttpclient.AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient();
    private static final String CLIENT_NAME = "AsyncHttpClient";

    public String getClientName() {
        return CLIENT_NAME;
    }

    @Override
    public BoundRequestBuilder newRequest(String url) {
        return asyncHttpClient.prepareGet(url);
    }

    @Override
    public void addHeaderToRequest(BoundRequestBuilder request, String key, String value) {
        request.addHeader(key, value);
    }

    @Override
    public Response execRequest(BoundRequestBuilder request, int requestNumber) throws Exception {
        try {
            ListenableFuture<Response> whenResponse = request.execute();
            return whenResponse.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    @Override
    public int getResponseStatusCode(Response response) {
        return response.getStatusCode();
    }

}
