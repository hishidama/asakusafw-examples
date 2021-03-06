/**
 * Copyright 2011 Asakusa Framework Team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.wordcount.jobflow;

import com.asakusafw.vocabulary.flow.Export;
import com.asakusafw.vocabulary.flow.FlowDescription;
import com.asakusafw.vocabulary.flow.Import;
import com.asakusafw.vocabulary.flow.In;
import com.asakusafw.vocabulary.flow.JobFlow;
import com.asakusafw.vocabulary.flow.Out;
import com.example.wordcount.jobflow.gateway.LogLineFromHdfs;
import com.example.wordcount.jobflow.gateway.WordCountToHdfs;
import com.example.wordcount.modelgen.dmdl.model.LogLine;
import com.example.wordcount.modelgen.dmdl.model.WordCount;
import com.example.wordcount.operator.WordCountOperatorFactory;

/**
 * JobFlow of Word Count.
 */
@JobFlow(name = "WordCountJob")
public class WordCountJob extends FlowDescription {

	WordCountOperatorFactory opeartor = new WordCountOperatorFactory();

	In<LogLine> in;
	Out<WordCount> out;

	public WordCountJob(
			@Import(name = "in", description = LogLineFromHdfs.class) In<LogLine> in,
			@Export(name = "out", description = WordCountToHdfs.class) Out<WordCount> out) {
		this.in = in;
		this.out = out;
	}

	@Override
	protected void describe() {
		out.add(opeartor.summlize(opeartor.split(in).result).result);
	}
}
