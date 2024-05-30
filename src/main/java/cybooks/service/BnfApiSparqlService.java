package cybooks.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import cybooks.model.Book;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Service class for managing books in the library system.
 */
public class BnfApiSparqlService {

    /**
     * Execute a SPARQL query against the BNF SPARQL API.
     *
     * @param sparqlQuery The SPARQL query to execute.
     * @return The JSON response from the BNF SPARQL API.
     */
    public JSONObject executeSparqlQuery(String sparqlQuery) {
        try {
            // Construct the URL with the SPARQL query
            //String urlString = "https://data.bnf.fr/sparql?default-graph-uri=&query=" + URLEncoder.encode(sparqlQuery); &format=application/json&timeout=0&should-sponge=
            String urlString = "https://data.bnf.fr/sparql?default-graph-uri=&query=" + URLEncoder.encode(sparqlQuery)+"&format=application/json&timeout=0&should-sponge=";
            URL url = new URL(urlString);

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Read the response from the API
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Return the JSON response
            JSONObject jsonResponse = new JSONObject(response.toString());
            return jsonResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Get a book by its ISBN.
     *
     * @param isbn The ISBN of the book to get.
     * @return The book with the given ISBN.
     */
    public JSONObject getBookWithISBN (String isbn) {
        String sparqlQuery = "PREFIX bnf-onto: <http://data.bnf.fr/ontology/bnf-onto/>" +
                "PREFIX dcterms: <http://purl.org/dc/terms/>" +
                "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
                "PREFIX rdarelationships: <http://rdvocab.info/RDARelationshipsWEMI/>" +
                "SELECT DISTINCT ?title ?author ?date ?publisher " +
                "WHERE {" +
                "?edition bnf-onto:isbn '"+isbn+"' ;" +
                "rdarelationships:workManifested ?work." +
                "?work dcterms:creator ?creator." +
                "?creator foaf:name ?author." +
                "?edition dcterms:date ?date; dcterms:title ?title; dcterms:publisher ?publisher." +
                "}";
        return executeSparqlQuery(sparqlQuery);
    }
    /**
     * Get books with a given title.
     *
     * @param title The title of the book to search for.
     * @return The JSON response from the BNF SPARQL API.
     */
    public Object getBooksWithTitle (String title) {
        String sparqlQuery = "PREFIX bnf-onto: <http://data.bnf.fr/ontology/bnf-onto/>" +
                "PREFIX dcterms: <http://purl.org/dc/terms/>" +
                "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
                "PREFIX rdarelationships: <http://rdvocab.info/RDARelationshipsWEMI/>" +
                "SELECT DISTINCT ?title ?author ?date ?publisher ?isbn " +
                "WHERE {" +
                "?edition bnf-onto:isbn ?isbn;" +
                "rdarelationships:workManifested ?work." +
                "?work dcterms:creator ?creator." +
                "?creator foaf:name ?author." +
                "?edition dcterms:date ?date; dcterms:title '"+title+"'; dcterms:publisher ?publisher." +
                "}";
        return executeSparqlQuery(sparqlQuery);
    }
    /**
     * Get books with a title that contains the given string.
     *
     * @param title The string to search for in the book titles.
     * @return The JSON response from the BNF SPARQL API.
     */
    public Object getBooksIsInTitle (String title) {
        String sparqlQuery = "PREFIX bnf-onto: <http://data.bnf.fr/ontology/bnf-onto/>" +
                "PREFIX dcterms: <http://purl.org/dc/terms/>" +
                "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" +
                "PREFIX rdarelationships: <http://rdvocab.info/RDARelationshipsWEMI/>" +
                "SELECT DISTINCT ?title ?author ?date ?publisher ?isbn " +
                "WHERE {" +
                "?edition bnf-onto:isbn ?isbn;" +
                "rdarelationships:workManifested ?work." +
                "?work dcterms:creator ?creator." +
                "?creator foaf:name ?author." +
                "?edition dcterms:date ?date; dcterms:title ?title; dcterms:publisher ?publisher." +
                "FILTER(regex(?title, '"+title+"', 'i')) " +
                "} GROUP BY ?isbn";
        return executeSparqlQuery(sparqlQuery);
    }
    /**
     * Check if a book with a given ISBN exists.
     *
     * @param isbn The ISBN of the book to check.
     * @return True if a book with the given ISBN exists, false otherwise.
     */
    public boolean checkIsbnExists(String isbn) {
        JSONObject bookData = getBookWithISBN(isbn);
        // Check if the JSON object is not null and contains book data
        if (bookData != null && bookData.has("results") && bookData.getJSONObject("results").has("bindings")) {
            JSONArray bindings = bookData.getJSONObject("results").getJSONArray("bindings");
            return bindings.length() > 0;
        }
        return false;
    }


}
