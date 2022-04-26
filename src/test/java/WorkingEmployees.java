import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WorkingEmployees {
    public static void main(String[] args) throws IOException,InterruptedException{
        CloseableHttpClient httpClient= HttpClients.createDefault();
        CloseableHttpResponse response;
        String mostLongName="";

        try {
            HttpGet request= new HttpGet("http://dummy.restapiexample.com/api/v1/employees");// Get request
            response=httpClient.execute(request);
            System.out.println("Status code: "+response.getStatusLine());
            HttpEntity entity = response.getEntity();

                if (entity!=null){
                    String result = EntityUtils.toString(entity);
                    //System.out.println(result); //Output all data from the query
                    JSONObject object = new JSONObject(result);
                    JSONArray array= object.getJSONArray("data");
                    mostLongName = array.getJSONObject(0).getString("employee_name");
                for (int i = 0; i < array.length(); i++) {
                    int post_id = array.getJSONObject(i).getInt("id");
                    String name= array.getJSONObject(i).getString("employee_name");
                    if (name.length() > mostLongName.length()) {
                        mostLongName = name;
                         }
                    System.out.println("id :" + post_id + "  employee_name :" + name );
                            }
                }
                response.close();
            } finally {
            httpClient.close();
                     }
            System.out.println("Most longest name is: " + mostLongName);
        //mostLongNam

        String postEndpoint = "http://dummy.restapiexample.com/api/v1/create";
        String inputJson = "{ \"name\":\"" + mostLongName +"_26-04-2022\", \"salary\":\"584111\", \"age\":\"111\" }";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(postEndpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(inputJson))
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse<String> response2 = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Status code: "+ response2.statusCode());
        System.out.println(response2.body());
    }
}


