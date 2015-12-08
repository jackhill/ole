package org.kuali.ole.dsng.rest.processor;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.common.SolrInputDocument;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Test;
import org.kuali.ole.DocstoreTestCaseBase;
import org.kuali.ole.docstore.common.constants.DocstoreConstants;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * Created by SheikS on 12/8/2015.
 */
public class OleDsNgOverlayProcessorTest implements DocstoreConstants {

    @Autowired
    OleDsNgRestAPIProcessor oleDsNgRestAPIProcessor;

    @Test
    public void testProcessOverlayForBib() throws Exception {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(LOCALID_DISPLAY,"10000034");

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);

        String savedJSON = oleDsNgRestAPIProcessor.processOverlayForBib(jsonArray.toString());
        assertTrue(StringUtils.isNotBlank(savedJSON));
    }

    @Test
    public void testSerializeAndDeserializeSolrInputDocumentAsJSON() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        SolrInputDocument solrInputDocument = new SolrInputDocument();
        solrInputDocument.addField("id",10101);
        solrInputDocument.addField("Title","Bib Title");
        solrInputDocument.addField("Author","Bib Author");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 10101);
        jsonObject.put("solrInputDocuemnt", objectMapper.writeValueAsString(solrInputDocument));

        String serializedContent = jsonObject.toString();
        assertTrue(StringUtils.isNotBlank(serializedContent));
        System.out.println(serializedContent);


        JSONObject jsonObject1 = new JSONObject(serializedContent);
        assertNotNull(jsonObject1);

        String solrInputDocuemntContent = (String) jsonObject1.getString("solrInputDocuemnt");
        SolrInputDocument deserializedDocument = objectMapper.readValue(solrInputDocuemntContent, SolrInputDocument.class);
        assertNotNull(deserializedDocument);
        System.out.println(deserializedDocument);


    }
}