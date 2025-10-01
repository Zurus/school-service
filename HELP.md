# Анализ дефекта DEF-91931

## Описание проблемы
Проект моделирует похожую ситуацию:
- Процесс `access` создает новый процесс `access_v2`
- При большой нагрузке делегат `SampleDelegateV2` процесса `access_v2` не подгружается

## Возможное решение
Заменить загрузку делегата по пакету на `delegate expression`

**Ссылка на документацию:**  
https://docs.camunda.org/manual/latest/user-guide/process-engine/delegation-code/

## Механизмы загрузки делегатов
Механизмы загрузки делегатов "по пакету" и "delegate Expression" отличаются

### Найденные классы Camunda
1. **Базовый класс сервис тасок** - `org.camunda.bpm.engine.impl.bpmn.behavior.TaskActivityBehavior`
2. **Обработчик с загрузкой "по пакету"** - `org.camunda.bpm.engine.impl.bpmn.behavior.ServiceTaskJavaDelegateActivityBehavior`
3. **Обработчик delegate Expression** - `org.camunda.bpm.engine.impl.bpmn.behavior.ServiceTaskDelegateExpressionActivityBehavior`
4. **Для загрузки "По пакету" используются**:
    - `org.camunda.bpm.engine.impl.util.ClassDelegateUtil`
    - `org.springframework.beans.factory.support.DefaultListableBeanFactory`

**Ссылка на похожий проект:**  
https://www.javainuse.com/boot/camunda/4

## Схема вызова и создания делегатов в Spring Boot + Camunda

### 1. SampleDelegate (delegateExpression="#{sampleDelegate}")

#### Создание Spring Bean

#### Вызов в Camunda

#### Классы-участники
**Создатели:**
- `ClassPathBeanDefinitionScanner` - обнаружение компонента
- `AbstractAutowireCapableBeanFactory` - создание бина
- `ConstructorResolver` - вызов конструктора

**Вызыватели:**
- `DelegateExpressionActivityBehavior` - обработка delegateExpression
- `SpringProcessApplicationElResolver` - резолвинг Spring бинов
- `BeanELResolver` - EL резолвер для бинов
- `JavaDelegateInvocation` - вызов execute()

### 2. SampleDelegateV2 (camunda:class="ru.schoolservice.arm.delegate.SampleDelegateV2")

#### Создание Spring Bean

#### Вызов в Camunda


#### Классы-участники
**Создатели:**
- `ClassPathBeanDefinitionScanner` - обнаружение компонента
- `AbstractAutowireCapableBeanFactory` - создание бина
- `ConstructorResolver` - вызов конструктора

**Вызыватели:**
- `ClassDelegate` - обработка camunda:class
- `DelegateFactory` - фабрика делегатов
- `SpringBeanDelegateFactory` - Spring-специфичная фабрика
- `BeanFactory` - доступ к Spring контексту
- `JavaDelegateInvocation` - вызов execute()

## Ключевые отличия механизмов

| Аспект | SampleDelegate | SampleDelegateV2 |
|--------|----------------|------------------|
| **Механизм резолвинга** | Поиск по имени бина "sampleDelegate" через EL выражение | Поиск по типу класса через Spring BeanFactory |
| **Точки входа Camunda** | `DelegateExpressionActivityBehavior` | `ClassDelegate` + `DelegateFactory` |
| **Spring интеграция** | `SpringProcessApplicationElResolver` + `BeanELResolver` | `SpringBeanDelegateFactory` + `BeanFactory` |

## Полный список классов для дебага

### Для SampleDelegate:
```java
// Spring создание
org.springframework.context.annotation.ClassPathBeanDefinitionScanner
org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory
ru.schoolservice.arm.delegate.SampleDelegate (конструктор и @PostConstruct)

// Camunda вызов
org.camunda.bpm.engine.impl.bpmn.behavior.DelegateExpressionActivityBehavior
org.camunda.bpm.engine.spring.application.SpringProcessApplicationElResolver
org.camunda.bpm.engine.impl.javax.el.BeanELResolver
```
### Для SampleDelegateV2
```
// Spring создание  
org.springframework.context.annotation.ClassPathBeanDefinitionScanner
org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory
ru.schoolservice.arm.delegate.SampleDelegateV2 (конструктор и @PostConstruct)

// Camunda вызов
org.camunda.bpm.engine.impl.bpmn.behavior.ServiceTaskBehavior
org.camunda.bpm.engine.impl.bpmn.parser.ClassDelegate
org.camunda.bpm.engine.spring.components.util.DelegateFactory
org.camunda.bpm.engine.spring.components.util.SpringBeanDelegateFactory
```

###Схема
```
SPRING BOOT START
    ↓
SampleDelegate Создан ←───────┐
    ↓                        │
SampleDelegateV2 Создан      │
    ↓                        │
CAMUNDA PROCESS              │
    ↓                        │
SampleDelegate:              │ SampleDelegateV2:
delegateExpression           │ camunda:class
    ↓                        │     ↓
SpringProcessApplication     │ ClassDelegate
    ↓                        │     ↓
BeanELResolver → "sampleDelegate" │ DelegateFactory → SampleDelegateV2.class
    ↓                        │     ↓
[Готовый Spring бин] → execute() │ [Готовый Spring бин] → execute()
```
