/*

Copyright 2010, Google Inc.
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are
met:

    * Redistributions of source code must retain the above copyright
notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above
copyright notice, this list of conditions and the following disclaimer
in the documentation and/or other materials provided with the
distribution.
    * Neither the name of Google Inc. nor the names of its
contributors may be used to endorse or promote products derived from
this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,           
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY           
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package com.google.refine.com.zemanta.model.recon;

import org.json.JSONObject;

import com.google.refine.model.Recon;
import com.google.refine.model.Recon.Judgment;
import com.google.refine.model.recon.ReconConfig;

abstract public class StrictReconConfig extends ReconConfig {
    //this should be sparql endpoint service
    final static protected String dbpediaReadService = "http://dbpedia.org/sparql";

    static public ReconConfig reconstruct(JSONObject obj) throws Exception {
        String match = obj.getString("match");
        if ("id".equals(match)) {
            return IdBasedReconConfig.reconstruct(obj);
        }
        return null;
    }
    
    @Override
    public Recon createNewRecon(long historyEntryID) {
        //TODO: check what's the proper identifier space and schema
        String identifierSpace = "http://dbpedia.org/";
        String schemaSpace = "http://dbpedia.org/ontology/";
        return new Recon(historyEntryID,identifierSpace,schemaSpace);
    }
    
    protected Recon createNoMatchRecon(long historyEntryID) {
        Recon recon = createNewRecon(historyEntryID);
        recon.service = "dbpedia";
        recon.judgment = Judgment.None;
        recon.matchRank = -1;
        return recon;
    }
}