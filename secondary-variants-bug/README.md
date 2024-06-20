# Secondary variants break consumable configuration with no artifacts

### No secondary variants

Invoke `./gradlew resolve`. 

`resolvableConfiguration` will consume `consumableConfiguration`.

That maybe also verified using `./gradlew :dependencyInsight --configuration resolvableConfiguration --dependency :`

### With a secondary variant
Invoke `./gradlew resolve -PregisterSecondaryVariant=true`. 

The resolution fails with the error message:
```
Could not determine the dependencies of task ':resolve'.
> Could not resolve all dependencies for configuration ':resolvableConfiguration'.
   > No variants of project : match the consumer attributes:
       - Configuration ':consumableConfiguration' variant myVariant declares attribute 'myAttribute1' with value 'value1':
           - Incompatible because this component declares attribute 'myAttribute2' with value 'otherValue2' and the consumer needed attribute 'myAttribute2' with value 'value2'
```

An interesting thing here is that `./gradlew :dependencyInsight -PregisterSecondaryVariant=true --configuration resolvableConfiguration --dependency :` does not show any problems. So, this makes the output of the task unreliable.

### With a secondary variant and some artifacts
Invoke `./gradlew resolve -PregisterSecondaryVariant=true -PregisterArtifacts=true`. 

Now the resolution works again as expected.

`./gradlew :dependencyInsight -PregisterSecondaryVariant=true -PregisterArtifacts=true --configuration resolvableConfiguration --dependency :` again proves this.