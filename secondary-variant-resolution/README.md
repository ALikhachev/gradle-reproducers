# Various problems around secondary variants

## Secondary variant cannot be selected without matching with the primary variant

### Steps to reproduce

* Invoke `./gradlew myTask`
You will see the matching failure:
```
Could not determine the dependencies of task ':myTask'.
> Could not resolve all dependencies for configuration ':myResolvableConfiguration'.
   > Could not resolve root project :.
     Required by:
         root project :
      > No matching variant of root project : was found. The consumer was configured to find attribute 'build_gradle.VariantType' with value 'secondary' but:
          - Variant 'myConsumableConfiguration':
              - Incompatible because this component declares attribute 'build_gradle.VariantType' with value 'primary' and the consumer needed attribute 'build_gradle.VariantType' with value 'secondary'
```
* Invoke `./gradlew myTask -PaddCompatibilityRule=true`
You will see that consumer is compatible with primary variant, but selects the secondary variant.

## Dependency insight gives a wrong match for secondary variants

### Steps to reproduce

* Invoke `./gradlew -PaddCompatibilityRule=true :dependencyInsight --configuration myResolvableConfiguration --dependency :`
You will see that it shows the primary variant even though actually secondary variant is used 
```
  Variant myConsumableConfiguration:
    | Attribute Name           | Provided | Requested |
    |--------------------------|----------|-----------|
    | build_gradle.VariantType | primary  | secondary |
```

Also, for some reason you will see `myResolvableConfiguration` there, however, it's not consumable. 
```
  Variant myResolvableConfiguration:
    | Attribute Name           | Provided  | Requested |
    |--------------------------|-----------|-----------|
    | build_gradle.VariantType | secondary | secondary |
```

## Secondary variants cannot be registered

### Steps to reproduce

* Invoke `./gradlew -PregisterForSecondaryVariant=true myTask`
You will see the failure
```
Could not determine the dependencies of task ':myTask'.
> Could not resolve all dependencies for configuration ':myResolvableConfiguration'.
   > Could not resolve root project :.
     Required by:
         root project :
      > Cannot create variant 'my-secondary-variant' after dependency configuration ':myConsumableConfiguration' has been resolved
```