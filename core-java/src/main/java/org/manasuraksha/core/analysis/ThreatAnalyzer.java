package org.manasuraksha.core.analysis;

import org.manasuraksha.core.model.AnalysisResult;

/** Common contract; T represents the input type (String, URI, etc.). */
@FunctionalInterface
public interface ThreatAnalyzer<T> {
    AnalysisResult analyze(T input);
}
