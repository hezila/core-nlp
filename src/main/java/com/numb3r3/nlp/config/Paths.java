package com.numb3r3.nlp.config;

// The MIT License
//
// Copyright (c) 2010 Stelios Karabasakis
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify_label, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.
//

/**
 * TODO Description missing
 *
 * @author Stelios Karabasakis
 */
public class Paths {

    public static final String BIN_ROOT = "senti_corpus";
    public static final String stopLemmasFile = BIN_ROOT
            + "/common-english-words.txt.txt";
    public static final String sentiWordnetFile = BIN_ROOT
            + "/SentiWordNet_3.0.0_20130122.txt";
    public static final String MPQAFile = BIN_ROOT
            + "/subjclueslen1-HLTEMNLP05.tff";
    public static final String AFINNFile = BIN_ROOT + "/AFINN-111.txt";
    public static final String GeneralInquirerFile = BIN_ROOT
            + "/GeneralInquirer.txt";

    public static final String CONFIG_ROOT = "config";
    public static final String jwnlConfigFile = CONFIG_ROOT
            + "/jwnl_file_properties.xml";

    public static final String WORKDIR_ROOT = "senti_output";
    public static final String stateFiles = WORKDIR_ROOT + "/state";
    public static final String backupStateFiles = WORKDIR_ROOT + "/state.bak";
    public static final String tokenListPath = WORKDIR_ROOT + "/tokenlists";
    public static final String lexiconPath = WORKDIR_ROOT + "/sentilexicon/";
    public static final String lexiconDiscardedPath = WORKDIR_ROOT
            + "/sentilexicon/discarded/";
    public static final String ruleSetFilesPath = WORKDIR_ROOT + "/ruleset/";
    public static final String dotFilesPath = WORKDIR_ROOT + "/treeviz/";


    public static final String MODEL_ROOT = "models";
    public static final String transactionPath = MODEL_ROOT + "/transactions.txt";
    public static final String freqNounsPath = MODEL_ROOT + "/camera_freqnouns.txt";

}