package com.github.wouterman.ruleengine.rules;

import com.github.wouterman.ruleengine.EvaluationResult;
import com.github.wouterman.ruleengine.model.CelestialObjectClassificationRequestDto;

public interface Rule {

  boolean shouldRun(CelestialObjectClassificationRequestDto celestialObject);

  EvaluationResult evaluate(CelestialObjectClassificationRequestDto celestialObject);

}