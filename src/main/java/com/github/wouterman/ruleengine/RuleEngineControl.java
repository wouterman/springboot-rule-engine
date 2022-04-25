package com.github.wouterman.ruleengine;

import static com.github.wouterman.ruleengine.ObjectType.UNKNOWN;
import static java.util.Collections.unmodifiableSet;

import com.github.wouterman.ruleengine.model.CelestialObjectClassificationRequestDto;
import com.github.wouterman.ruleengine.model.CelestialObjectClassificationResponseDto;
import com.github.wouterman.ruleengine.rules.BlackHoleRule;
import com.github.wouterman.ruleengine.rules.PlanetRule;
import com.github.wouterman.ruleengine.rules.Rule;
import com.github.wouterman.ruleengine.rules.StarRule;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class RuleEngineControl {

  private static final Set<Rule> rules = Set.of(new PlanetRule(), new StarRule(),
      new BlackHoleRule());


  public CelestialObjectClassificationResponseDto classify(
      final CelestialObjectClassificationRequestDto celestialObject) {
    ObjectType objectType = UNKNOWN;

    final List<EvaluationResult> matchedRules = rules
        .stream()
        .filter(rule -> rule.shouldRun(celestialObject))
        .map(rule -> rule.evaluate(celestialObject))
        .collect(Collectors.toList());

    if (!CollectionUtils.isEmpty(matchedRules)) {
      if (matchedRules.size() > 1) {
        matchedRules.sort(Comparator.comparing(EvaluationResult::getPrecedence));
      }
      objectType = matchedRules.get(0).getType();
    }

    return CelestialObjectClassificationResponseDto.builder().type(objectType.name()).build();
  }

}
