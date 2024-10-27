
![header](https://capsule-render.vercel.app/api?type=venom&color=auto&height=300&section=header&text=CalIT&fontSize=50&desc=📆Optimize%20Your%20Workspace%20Scrum%20Management&descAlignY=60)

# 👀 팀원 구성

<br>

|                    **👑차윤슬**                      |                  **박성준**                   |                 **최승은**                  |                     **강혜정**                     |             **지연희**              |
|:------------------------------------------------:|:------------------------------------------:|:----------------------------------------:|:-----------------------------------------------:|:--------------------------------:|
| <img src="https://github.com/user-attachments/assets/8d255376-5ae9-4685-8f11-cd4b18a4bb55" width="128px"/> | <img src="https://github.com/user-attachments/assets/f49055cf-2b4d-41ac-bb7d-98b47d257c4b" width="128px"/> | <img src="https://github.com/user-attachments/assets/21e6cee5-c2f0-4c94-9a0a-938053c5342b" width="128px"/> | <img src="https://github.com/user-attachments/assets/813020ee-ef97-4f44-becd-38ef55a778b1" width="128px"/> | <img src="https://github.com/user-attachments/assets/cefb90f7-237b-4613-b6e2-89e1c40c00f3" width="128px"/> |
| [@yunseul-dev](https://github.com/yunseul-dev) | [@seongxun](https://github.com/seongxun) | [@xeunnie](https://github.com/xeunnie) | [ @hyejeung](https://github.com/hyejeung) | [@Aqulog](https://github.com/Aqulog) |



# ⚒️ 기술 스택



### 백엔드
<img src="https://img.shields.io/badge/SpringBoot-181717?style=flat&logo=SpringBoot&logoColor=6DB33F&color=white" alt=""> <img src="https://img.shields.io/badge/Spring_Security-181717?style=flat&logo=SpringSecurity&logoColor=6DB33F&color=white" alt=""> <img src="https://img.shields.io/badge/JSON_Web_Tokens-181717?style=flat&logo=JSONWebTokens&logoColor=000000&color=white" alt=""> <img src="https://img.shields.io/badge/Spring-181717?style=flat&logo=Spring&logoColor=6DB33F&color=white" alt=""> <img src="https://img.shields.io/badge/Spring_Batch-181717?style=flat&logo=Spring&logoColor=6DB33F&color=white" alt=""> <img src="https://img.shields.io/badge/Apache_Kafka-181717?style=flat&logo=ApacheKafka&logoColor=231F20&color=white" alt=""> <img src="https://img.shields.io/badge/n8n-181717?style=flat&logo=n8n&logoColor=0F74E2&color=white" alt=""> <img src="https://img.shields.io/badge/Redis-181717?style=flat&logo=Redis&logoColor=DC382D&color=white" alt="">
### 데이터베이스
<img src="https://img.shields.io/badge/MariaDB-181717?style=flat&logo=MariaDB&logoColor=003545&color=white" alt=""> <img src="https://img.shields.io/badge/PostgreSQL-181717?style=flat&logo=PostgreSQL&logoColor=336791&color=white" alt=""> 


---



# 🎯 소프트웨어 아키텍처
<details>
  <summary><b>레이어드 아키텍처 + 멀티모듈 아키텍처</b></summary>
  
## 레이어드 아키텍처(Layerd Architecture)
  
  > 레이어드 아키텍처는 소프트웨어를 여러 계층으로 나누어 설계하는 방식입니다. 각 레이어는 정해진 역할을 가집니다.

1. 레이어 간의 책임이 명확하게 분리되어 있어 코드 변경 시 영향 범위를 최소화할 수 있습니다.
2. 레이어 간의 의존 흐름이 일정하여 새로운 기능 개발 시 통일된 프로세스로 빠르게 개발할 수 있었습니다.

<br/>

## 멀티모듈 아키텍처(MultyModule Architecture)
  
> 멀티모듈 아키텍처는 여러 개의 작은 단위 모듈로 소프트웨어를 나누어 설계하는 방식입니다. 각 모듈은 독립적으로 개발되고 배포될 수 있으며, 모듈 간 재사용성을 높여 전체 시스템의 복잡성을 효율적으로 관리할 수 있습니다.

1. 공통 모듈을 여러 프로젝트에서 재사용할 수 있어 코드 중복을 줄일 수 있습니다.
2. 각 모듈이 독립적으로 개발되고 배포될 수 있어 개발 및 테스트의 효율성이 향상됩니다.
3. 기존 모듈을 확장하기 쉽기 때문에 전체 시스템의 복잡성을 효율적으로 관리 가능합니다.

### 1. 멀티모듈 아키텍처 도입 배경 
 프로젝트 초기에는 모든 기능이 단일 모듈로 구성되어 있었습니다. 하지만, 기능이 다양해지고 서버의 역할이 분리될 필요가 생기면서, 각각의 책임에 맞게 모듈을 분리해야 했습니다. 따라서, 프로젝트를 Batch, Common, API-Server라는 세 모듈로 나누었습니다.


![image](https://github.com/user-attachments/assets/4a9a9fa4-4c33-42a4-b97a-27d22d961a3a)

 
### 2. 모듈 구성 및 역할
- **Common 모듈** <br/>
  공통적으로 사용되는 코드와 설정을 관리합니다. 주로 데이터베이스 관련 설정, 공통 유틸리티 클래스 등이 포함되어 있습니다.
- **API-Server 모듈** <br/>
  사용자 요청을 처리하고, 실시간 SSE 알람을 제공하는 등 웹 애플리케이션의 중심이 되는 모듈입니다. 스프링 부트 웹 의존성을 가지고 있어 SSE 알람 서비스를 구현할 수 있습니다.
- **Batch 모듈** <br/>
   일정 작업을 주기적으로 실행하는 서버로, 리마인드 알람 작업 등 배치 처리를 담당합니다. 스프링 배치를 사용하여 멀티 스레드 기반으로 작업을 효율적으로 처리하고, 한 번 실행된 후 종료됩니다.

### 3. 주요 이슈 및 해결 방안

> 멀티모듈화를 진행하면서 SSE 알람 서비스의 의존성 문제가 있었습니다. SseEmitter 클래스를 사용하기 위해서는 spring-boot-web 의존성이 필요했으나, 이 의존성은 배치 모듈의 특성과 맞지 않았습니다. 

이 문제를 해결하기 위해 SSE 알람 전송 로직은 API-Server 모듈에 남겨두어 클라이언트와의 실시간 통신을 유지할 수 있도록 했습니다. 또한 모듈 간 결합도를 낮추기 위해 **Kafka를 사용하여 모듈 간 메시지를 전달하는 구조**를 도입했습니다. Batch 모듈에서 알람 메시지를 Kafka를 통해 전달하면, API-Server 모듈이 이를 수신하여 적절한 SSE 알람을 클라이언트에게 보냅니다. 이를 통해 모듈 간 결합도를 낮추고, 새로운 기능이 추가되더라도 서로의 변경에 영향을 덜 받도록 설계할 수 있었습니다.

### 4. 구조 개선의 효과

- **유지보수성 및 확장성 증가** <br/>
  각 모듈이 독립적으로 개발 및 확장할 수 있는 구조로 변경되었습니다.

- **결합도 감소** <br/>
  모듈 간 의존성을 줄여 변경에 유연하게 대응할 수 있었습니다.

- **성능 최적화** <br/>
  Batch 모듈은 멀티 스레드를 활용한 배치 작업으로 리소스 사용을 최적화하고, API-Server 모듈은 실시간 통신을 유지하는 구조로 설계하여 성능을 개선했습니다.

<br/>

## 레이어드 아키텍처 + 멀티모듈 아키텍처
레이어드 아키텍처를 기반으로 API 서버와 공통 모듈로 구성된 멀티 모듈 구조를 채택 함으로써 API 서버는 주요 비즈니스 로직을 담당하고, 공통 모듈은 엔티티와 같은 재사용 가능한 컴포넌트를 관리하여 모듈 간의 중복을 줄이고 코드의 재사용성을 높였습니다.


</details>


<br>

# ✨ 주요 기능
<details>
  <summary><b>캘린더 & 대쉬보드</b></summary>
  <div markdown="1">

![image](https://github.com/user-attachments/assets/4f08ce80-c1fc-4777-ae44-edef10c648ad)


## 조회 기능 개선: JPQL에서 QueryDSL로의 전환

> 캘린더와 대시보드에서는 스프린트, 태스크, 회의 등 다양한 상황으로 조회가 필요했습니다. 조회 기능을 개선하기 위해 기존 JPQL 코드를 QueryDSL로 리팩토링하여 동적 쿼리를 적용함으로써, 코드의 가독성과 성능을 모두 향상시켰습니다.

### QueryDSL 전환의 장점

1. **동적 쿼리 작성 용이**
    
    QueryDSL은 코드 기반으로 쿼리를 작성하기 때문에, 조건에 따라 유연하게 동적 쿼리를 생성할 수 있습니다. 이를 통해 복잡한 조회 조건을 쉽게 처리할 수 있으며, 코드의 가독성도 높아집니다.
    
2. **컴파일 타임 안전성**
    
    JPQL은 문자열 기반이므로 런타임 오류가 발생할 가능성이 높습니다. 반면, QueryDSL은 Java 코드로 작성되기 때문에, 쿼리 작성 시점에 컴파일 타임에서 오류를 감지할 수 있기 때문에 코드의 안정성을 강화할 수 있습니다.
    
3. **복잡한 조인 처리의 간결화**
    
    QueryDSL은 엔티티 간의 조인이나 서브쿼리를 쉽게 표현할 수 있어, 복잡한 조회 로직을 간결하게 작성할 수 있습니다.
    
4. **N+1 문제 해결**
    
    QueryDSL을 사용하면 **페치 조인(Fetch Join)**을 통해 연관된 엔티티를 한 번의 쿼리로 함께 조회할 수 있습니다. 이를 통해 JPQL에서 자주 발생하는 N+1 문제를 방지하고, 데이터베이스 접근 횟수를 줄여 성능을 더욱 최적화할 수 있습니다.
    

### 성능 개선 사항

- **불필요한 데이터 조회 최소화**
    
    QueryDSL로 리팩토링하면서, 필요한 필드만 선택적으로 조회하도록 최적화했습니다. 이를 통해 데이터 전송량을 줄이고, 데이터베이스의 부하를 줄일 수 있었습니다.
    
- **쿼리 성능 향상 및 N+1 문제 해결**
    
    동적 쿼리를 작성할 때 조건을 유연하게 추가하고, 페치 조인을 사용해 연관된 엔티티를 한 번의 쿼리로 조회하여 N+1 문제를 해결했습니다. 그 결과, 조회 기능의 응답 속도가 크게 향상되었습니다.

  </div>
</details>

<details>
  <summary><b>채팅</b></summary>
  <div markdown="1">

### 1. WebSocket & STOMP
#### **사용 기술 및 적용 이유**

![stomp](https://github.com/user-attachments/assets/d546f308-987a-4ba3-8011-973d94144a63)

<br>

**WebSocket:** 실시간 양방향 통신을 지원하여, 채팅 시스템에서 사용자 간 빠르고 지속적인 메시지 교환이 가능합니다. <br>
**STOMP (Simple Text Oriented Messaging Protocol):** WebSocket 위에서 동작하는 메시징 프로토콜로, 메시지 라우팅을 유연하게 설정할 수 있습니다. **채팅방 ID**를 포함한 경로를 통해 각 채팅방의 구독자들에게 메시지를 정확하게 전달하는 데 활용됩니다.

#### 적용 예시

채팅방마다 `/sub/room/{chatRoomId}` 형식으로 구독 경로를 설정하여, 해당 채팅방에 속한 사용자들이 메시지를 실시간으로 주고받을 수 있게 하였습니다.
### 2. 메시지 전송 흐름
#### **메시지 처리 방식**

**웹소켓을 통한 전송:** 사용자가 웹소켓을 통해 특정 경로로 메시지를 전송합니다. <br>
**인메모리 브로커:** 전송된 메시지는 인메모리 브로커에서 카프카 프로듀서로 전달됩니다. <br>
**카프카 토픽 저장:** 프로듀서는 메시지를 토픽에 직렬화하여 저장합니다. <br>
**컨슈머 메시지 처리:** 컨슈머는 해당 토픽에서 메시지를 읽고 역직렬화한 후 다시 인메모리 브로커로 전달합니다. <br>
**구독자에게 메시지 전달:** 브로커는 메시지를 구독 경로에 맞춰 구독자들에게 실시간으로 전달합니다. <br>

### 3. Kafka
#### **사용 기술 및 적용 이유**

![kafka](https://github.com/user-attachments/assets/213ce4a8-202b-479d-b010-1607480929e5)

<br>

**확장성 및 신뢰성:** STOMP 기반의 세션 관리는 단일 서버 환경에서는 한계가 있었습니다. 다중 서버 환경에서 메시지 손실과 확장성 문제를 해결하기 위해 **Kafka**를 도입하였습니다. Kafka는 사용자가 다른 서버에 접속해도 메시지를 안정적으로 전달받을 수 있도록 해줍니다. <br>
**세션 관리:** 여러 서버를 운영하는 환경에서 Kafka를 통해 서버 간 세션을 관리하여, 메시지의 일관성과 신뢰성을 보장하였습니다.

#### 적용 예시
Kafka의 Pub-Sub 모델을 활용하여 동일한 토픽을 구독한 클라이언트들에게 안정적으로 메시지를 전송하고, 여러 서버 간의 세션 관리 문제를 해결하였습니다.

### 4. 개선 사항
#### Kafka 도입 후 성능 개선

STOMP만으로 운영 시 발생할 수 있는 확장성 문제와 메시지 손실 문제를 해결하였습니다. Kafka의 Pub-Sub 모델을 통해 서버 간 세션을 안정적으로 관리할 수 있었으며, 메시지 전송의 신뢰성을 크게 높였습니다.
    
  </div>
</details>
<details>
  <summary><b>알람</b></summary>
  <div markdown="1">


## Spring Batch를 통한 알람 기능 개선

> 기존에는 **`@Scheduled`** 어노테이션을 사용한 스케줄링 방식으로 리마인드 알람을 구현했지만, 서버 부하와 메모리 사용량 증가로 인해 안정적인 서비스 제공에 어려움이 있었습니다. 이를 해결하기 위해 Spring Batch를 도입하여 알람 기능을 개선했습니다.

### 배치 적용 후 개선 사항

1. **역할 분리**
    
    Spring Batch의 Job, Step, Chunk 구조를 사용하여 리마인드 알람 기능을 단계별로 구현했습니다. 각 알람 유형(회의, 스프린트, 태스크)에 대해 독립적인 배치 작업을 설정하여 관리함으로써 코드의 책임을 명확히 하고 작업의 독립성을 유지했습니다.
    
    - **ItemReader**: 데이터베이스에서 회의, 스프린트, 태스크의 마감일 기준으로 알람 대상 데이터를 조회합니다.
    - **ItemProcessor**: 데이터를 검증하고 알람 메시지에 필요한 정보를 가공하여, 알람 시점에 맞는 데이터를 설정합니다.
    - **ItemWriter**: 가공된 데이터를 Kafka를 통해 **`reminder-alarm`** 토픽으로 전송하여 알람 메시지를 처리합니다.
2. **부하 분산**
    - 알람 처리 로직을 독립적인 배치 서버로 분리해 메인 서버의 부하를 줄이고, 작업 분산을 통해 안정적인 서비스를 제공했습니다.
    - 배치 서버에서 발생하는 오류가 메인 서버에 미치는 영향을 최소화했습니다.
3. **확장성 강화**
    - Spring Batch는 배치 모듈에서 알람 메시지를 Kafka를 통해 API 서버로 전달하고, API 서버는 클라이언트에게 알람을 전송합니다.
    - 이는 배치 모듈과 실시간 알람 처리(SSE Emitter)의 의존성 문제를 해결하고, 모듈 간 결합도를 낮춰 확장성과 유연성을 높였습니다.
4. **Kubernetes CronJob을 통한 배치 스케줄링**
    - Kubernetes 환경에서 Spring Batch 작업을 주기적으로 실행하기 위해 CronJob을 사용했습니다.
    - CronJob을 통해 배치 작업을 정기적으로 실행하여 특정 시간에 맞춰 알람 기능을 수행했습니다.
  
   <img src="https://github.com/user-attachments/assets/c9c8d171-0eec-4994-8191-61fbcf3b764e" width="800" heigh="400" />


  
### 배치 적용 후 성능 비교

프로메테우스와 그라파나로 모니터링한 결과, 도입 전에는 알람 발생 시 CPU 사용량이 급증했으나, 도입 후에는 API 서버의 CPU 사용량이 감소하고 배치 서버에 부하가 분산되는 것을 확인할 수 있었습니다. 

**[배치 도입 전 테스트 결과]**

![image](https://github.com/user-attachments/assets/5677f68a-c56b-4199-97b9-fb4fce7e5469)

**[배치 도입 후 테스트 결과]**
![image](https://github.com/user-attachments/assets/bb925ba8-b415-4a81-b41c-609c2210672b)

- 분산 처리 환경을 구축한 결과, 배치 작업 중에도 다른 API 요청에 영향을 주지 않고 서버의 안정성을 유지할 수 있었습니다.
- 멀티스레드를 활용해 병렬 처리한 결과 알람 처리 속도를 11% 향상시켜 전반적인 시스템 성능을 최적화했습니다.


  </div>
</details>
<details>
  <summary><b>회의록 공동작성</b></summary>
  <div markdown="1">

## 1. Redis

### 인메모리 데이터베이스
- **Redis**는 디스크 기반의 RDB와 달리 **인메모리**에서 데이터를 처리하여 **훨씬 빠른 성능**을 제공합니다.
- 일반적인 인메모리 DB와 달리 Redis는 **영속성**을 지원하여, 장애 발생 시에도 데이터를 복구할 수 있습니다.

### 회의록 공동작성 기능에 Redis 적용 이유
- **빠른 읽기 작업**이 중요한 실시간 공동 작성 환경에 적합해서 선택하였습니다.

### Lookaside 캐시 패턴
- **캐시에서 먼저 데이터를 조회**하고, 데이터가 없을 경우 DB에서 조회하여 캐시에 저장하는 방식입니다.
- 반복적인 읽기 작업에서 **성능 향상**을 기대할 수 있으며, **DB 조회 빈도**를 줄여, 캐시를 활용한 효율적인 읽기 작업이 가능합니다.

## 2. Kafka

### 세션 관리 기능
- 서버를 2대 운영하는 환경에서 **Kafka**를 활용하여 세션 관리 기능을 구현하였습니다.
- 클라이언트 간의 원활한 통신을 위해 Kafka를 선택하였으며, **Pub-Sub 모델**을 기반으로 클라이언트 간에 메시지를 주고받습니다.

### Kafka의 장점
- 동일한 **토픽을 구독한 클라이언트들**에게 메시지를 전송하여, **메시지의 일관성**과 **신뢰성**을 보장합니다.
- **서버 간 세션 연결**를 유지할 수 있습니다.

## 3. 시스템 구성도

- **서버 간 세션 연결** 을 위해 kafka 를 활용해 클라이언트간의 연결을 유지시켰습니다.
- **성능 최적화** 를 위해 **LookaSide 패턴**을 적용하여 읽기 작업의 효율성을 높였습니다.

<img width="877" alt="스크린샷 2024-10-24 오후 9 34 26" src="https://github.com/user-attachments/assets/e4ad109f-fae3-47a1-8bdb-fa8ae98f4986">

## 4. REDIS 적용 후 성능 개선

**[REDIS 도입 전 테스트 결과]**

![성능개선전](https://github.com/user-attachments/assets/5cf1459a-61b7-4a71-a7fd-81f7599328d4)

- REDIS 를 도입하기 전 사용자가 작성할 때마다 DB에 업데이트가 되어 성능에 문제가 발생하였습니다.

**[REDIS 도입 후 테스트 결과]**

![성능개선후](https://github.com/user-attachments/assets/2478e3bc-3829-4681-9659-63cfb597c918)

- REDIS 를 도입해 평균 응답 시간이 총 65% 감소했으며, DB 성능 저하 문제도 해결하였습니다.




  </div>
</details>

<details>
  <summary><b>AI</b></summary>
  <div markdown="1">
    
## N8N? 🤔

**n8n**은 자동화된 워크플로우를 구성할 수 있는 오픈소스 툴로, 다양한 서비스와의 통합을 통해 복잡한 작업들을 시각적으로 간편하게 자동화할 수 있게 해줍니다. 특히 워크플로우의 커스텀 설정이 가능해, 필요에 따라 유연하게 구성할 수 있는 것이 큰 장점입니다.

**CalIT 프로젝트의 챗봇**은 이러한 **n8n**을 활용하여 구현되었습니다. <br>
**n8n**을 사용한 이유는 사용자로부터 검색된 **지식 정보**를 활용하여 **사실에 기반**한 답변을 생성하고, 최신 기술을 통합하여 성능을 최적화하기 위함입니다.

## 챗봇 구조

챗봇의 **기본 구조**는 다음과 같습니다:

![작성 워크플로우](https://github.com/user-attachments/assets/d26cfe96-38d0-4952-aed9-6b503e55ac9a)

1. **사용자 요청 처리**
   - 클라이언트가 서버에 메시지를 보내면, 서버는 n8n의 webhook을 호출하여 정의된 워크플로우를 실행합니다.

2. **워크플로우 실행**
   - 이 워크플로우 내에서 원하는 동작을 커스텀할 수 있기 때문에 CalIT 프로젝트의 구조에 맞도록 직접 설정하였습니다.
   - n8n의 자동화된 워크플로우를 사용하여, 최신 기술을 도입하고 성능을 최적화하였습니다.

## 구현된 워크플로우

### 📨 회의 자료 추천 워크플로우

![ai 회의 자료 추천](https://github.com/user-attachments/assets/2e37a546-6b28-42d3-8b5f-db38b1a0aeb4)

- 사용자가 회의 자료를 요청하면, 웹훅이 동작하고 데이터베이스에 접근하여 관련 정보를 받아옵니다.
- **Information Extractor**에서 중요한 키워드를 추출합니다.
- 이 키워드는 AI 에이전트가 Self API, Output Parser, OpenAI 모델을 통해 처리되며, JSON 형태로 서버에 전달됩니다.

### 📜 회의록 요약 워크플로우

![ai 회의록 요약](https://github.com/user-attachments/assets/f38039dc-5078-4c15-bca7-ee214cfde63f)

  - **Summarization Chain**은 긴 회의록이나 문서를 요약하고 핵심 내용을 추출하는 데 매우 유용합니다.
  - 이를 통해 AI 모델과 연동해 더 효율적이고 간결한 요약 작업을 구현하였습니다.

## 기대 효과

**n8n**을 활용한 자동화된 워크플로우는 단순히 지식을 검색하여 제공하는 것에 그치지 않고, AI와의 연동을 통해 더욱 풍부하고 신뢰성 있는 답변을 사용자에게 제공합니다. <br>
또한, **시각적인 워크플로우 빌더**를 통해 개발자와 비개발자 모두 쉽게 챗봇의 동작을 이해하고 수정할 수 있는 장점을 가지고 있습니다.


  </div>
</details>




