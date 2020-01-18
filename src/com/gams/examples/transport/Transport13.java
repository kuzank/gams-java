package com.gams.examples.transport;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.gams.api.GAMSVariableRecord;
import com.gams.api.GAMSWorkspace;
import com.gams.api.GAMSWorkspaceInfo;

/**
 * This example demonstrates how to run a job via the wrapper model of 
 * a GAMS [trnsport] from the transportation problem based from the GAMS 
 * Model Library. 
 */
public class Transport13 {

    public static void main(String[] args) {
        List<String> plants = Arrays.asList("Seattle", "San-Diego");
        List<String> markets = Arrays.asList("New-York", "Chicago", "Topeka");
        Map<String, Double> capacity = new HashMap<String, Double>();
        {
             capacity.put("Seattle", Double.valueOf(350.0));
             capacity.put("San-Diego", Double.valueOf(600.0));
        }
        Map<String, Double> demand = new HashMap<String, Double>();
        {
             demand.put("New-York", Double.valueOf(325.0));
             demand.put("Chicago", Double.valueOf(300.0));
             demand.put("Topeka", Double.valueOf(275.0));
        }

        Map<Vector<String>, Double> distance = new HashMap<Vector<String>, Double>();
        {
            distance.put( new Vector<String>( Arrays.asList(new String[]{"Seattle", "New-York"}) ), Double.valueOf(2.5));        
            distance.put( new Vector<String>( Arrays.asList(new String[]{"Seattle", "Chicago"}) ),  Double.valueOf(1.7));
            distance.put( new Vector<String>( Arrays.asList(new String[]{"Seattle", "Topeka"}) ),  Double.valueOf(1.8));
            distance.put( new Vector<String>( Arrays.asList(new String[]{"San-Diego", "New-York"}) ), Double.valueOf(2.5));
            distance.put( new Vector<String>( Arrays.asList(new String[]{"San-Diego", "Chicago"}) ),  Double.valueOf(1.8));
            distance.put( new Vector<String>( Arrays.asList(new String[]{"San-Diego", "Topeka"}) ),   Double.valueOf(1.4));
        }

        GAMSWorkspaceInfo  wsInfo  = new GAMSWorkspaceInfo();
        if (args.length > 0)
            wsInfo.setSystemDirectory( args[0] );

        File workingDirectory = new File(System.getProperty("user.dir"), "Transport13");
        workingDirectory.mkdir();
        wsInfo.setWorkingDirectory(workingDirectory.getAbsolutePath());

        GAMSWorkspace ws = new GAMSWorkspace(wsInfo);

        TransportModel t = new TransportModel(ws);

        for(String p : plants)
            t.geti().addRecord(p);

        for(String m : markets)
            t.getj().addRecord(m);

        for(String p : plants)
            t.geta().addRecord(p).setValue( capacity.get(p) );

        for(String m : markets)
            t.getb().addRecord(m).setValue( demand.get(m) );

        for(Vector<String> vd : distance.keySet()) {
            String[] keys = new String[vd.size()];
            vd.toArray(keys);
            t.getd().addRecord( keys ).setValue( distance.get(vd).doubleValue() );
        }

        t.getf().addRecord().setValue( 90 );
        
        t.getopt().setAllModelTypes( "cplex" );

        t.run(System.out);

        System.out.println("Objective: " + t.getz().getFirstRecord().getLevel());

        for(GAMSVariableRecord rec: t.getx())
            System.out.println("x(" + rec.getKey(0) + "," + rec.getKey(1) + "): level=" + rec.getLevel() + " marginal=" + rec.getMarginal());

    }
}