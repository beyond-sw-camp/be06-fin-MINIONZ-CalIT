![일단 리드미](https://github.com/user-attachments/assets/e5f948ee-5726-400c-b519-18ff668f873c)

# 👀 팀원 구성

팀장: 차윤슬 <br>
팀원: 박성준, 최승은, 강혜정, 지연희

|                     **차윤슬**                      |                  **박성준**                   |                 **최승은**                  |                     **강혜정**                     |             **지연희**              |
|:------------------------------------------------:|:------------------------------------------:|:----------------------------------------:|:-----------------------------------------------:|:--------------------------------:|
| <img src="https://soopool.art/img/infoacna/i/Judy/192px-Judy's_Poster_NH_Texture.png" width="128px"/> | <img src="https://soopool.art/img/infoacna/i/Ace/128px-Ace's_Poster_NH_Texture.png"/> | <img src="https://soopool.art/img/infoacna/i/Rio/128px-Rio's_Poster_NH_Texture.png"/> | <img src="https://soopool.art/img/infoacna/i/Felicity/128px-Felicity's_Poster_NH_Texture.png"/> | <img src="https://soopool.art/img/infoacna/i/Rosie/128px-Rosie's_Poster_NH_Texture.png"/> |
| [@yunseul-dev](https://github.com/yunseul-dev) | [@seongxun](https://github.com/seongxun) | [@xeunnie](https://github.com/xeunnie) | [ @hyejeung](https://github.com/hyejeung) | [@Aqulog](https://github.com/Aqulog) |



<br/>

# 🌟 프로젝트 목표

빠른 개발 속도와 품질 향상

	1. 개발 속도 향상
       CI/CD를 통해 개발자들이 코드를 더욱 빠르게 통합하고 배포할 수 있습니다. 자동화된 프로세스로 인해 통합과 배포가 간소화되어 전체 개발 주기가 단축됩니다.
	2. 자동화된 프로세스:
       빌드, 테스트, 배포 등 반복적인 작업을 자동화하여 개발자는 코드 작성에 더 많은 시간을 투자할 수 있습니다. 이는 생산성 향상으로 이어지며, 실수 발생 가능성을 줄입니다.
	3. 반복 작업의 최소화:
       소스 코드 통합 시 수작업을 줄이고, 자동화된 테스트와 무중단 배포, 이전 버전으로의 롤백 등 불필요한 반복 작업을 최소화합니다. 이를 통해 개발 효율성을 극대화하고 오류를 방지할 수 있습니다.
	4. 지속적인 품질 관리:
       CI/CD를 통해 개발 주기 내내 지속적으로 코드 품질을 유지하고 관리할 수 있습니다. 자동화된 테스트와 배포로 안정적인 운영 환경을 유지하며, 문제 발생 시 신속한 대응이 가능합니다.

# ⚒️ 기술 스택

### CI/CD

<img src="https://img.shields.io/badge/Git-000?style=style&logo=Git&logoColor=F05032&color=white" alt=""> <img src="https://img.shields.io/badge/GitHub-181717?style=flat&logo=GitHub&logoColor=black&color=white" alt=""> <img src="https://img.shields.io/badge/Jenkins-D24939?style=flat&logo=jenkins&logoColor=D24939&color=white" alt=""/> <img src="https://img.shields.io/badge/Docker-2496ED?style=flat&logo=Docker&logoColor=2496ed&color=white" alt=""/> <img src="https://img.shields.io/badge/Kubernetes-326CE5?style=flat&logo=Kubernetes&logoColor=326ce5&color=white" alt=""/>

### 모니터링
<img src="https://img.shields.io/badge/Prometheus-181717?style=flat&logo=Prometheus&logoColor=E6522C&color=white" alt=""> <img src="https://img.shields.io/badge/Grafana-181717?style=flat&logo=Grafana&logoColor=F46800&color=white" alt="">

### 웹 애플리케이션 프로그램
<img src="https://img.shields.io/badge/Vue-181717?style=flat&logo=Vue.js&logoColor=4FC08D&color=white" alt=""> <img src="https://img.shields.io/badge/NGINX-181717?style=flat&logo=NGINX&logoColor=009639&color=white" alt=""> <img src="https://img.shields.io/badge/SpringBoot-181717?style=flat&logo=SpringBoot&logoColor=6DB33F&color=white" alt="">

<br>
<br>

# 🌏 Devops 운영 환경

### Kubernetes 클러스터 노드 구성
💻 Master 1대, 💻 Worker 4대의 클러스터 구성<br>
➡️ 이 구조를 통해 운영 환경에서의 일관성과 안정성을 유지하면서도 개발 프로세스의 효율성을 크게 높였습니다.

### 기대효과
    • 안정적인 프라이빗 클라우드 운영:
      Kubernetes는 프라이빗 클라우드 인프라에서 안정적으로 실행될 수 있는 기능을 제공합니다. 이를 통해 서비스가 안정적으로 운영되며, 개발 및 배포 과정이 더욱 효율적으로 진행되었습니다.
	• 클러스터 구성:
      클러스터는 마스터 노드 1대와 워커 노드 4대로 구성되었습니다. 이러한 구성은 안정적이고 확장 가능한 서비스 운영을 가능하게 하였습니다.
<details>
    <summary>
<span style="font-size:150%"> k8s 전체 서비스 아키텍쳐 </span></summary>


<p>
	
![Group 29](https://github.com/user-attachments/assets/b67837ed-2108-42a6-a81e-e8255a44b3cb)


</details>
<br>
<br>

# 📽️ CI/CD 시나리오

GitHub, Jenkins, Docker, Kubernetes를 활용하여 프론트엔드와 백엔드 애플리케이션을 각각 자동화하는 과정의 시나리오입니다.

#### 1. 프로젝트 개요
프론트엔드(Vue)와 백엔드(SpringBoot)로 구성된 웹 애플리케이션입니다.
GitHub에 호스팅되며, CI/CD 파이프라인을 통해 자동으로 빌드, 테스트, Docker 이미지 생성 및 Kubernetes 클러스터로의 배포가 이루어집니다.
CI/CD 파이프라인은 Jenkins를 통해 프론트와 백엔드 각각 관리되며, 모든 빌드와 배포 과정은 Docker와 Kubernetes를 사용해 자동화됩니다.

#### 2. CI/CD 파이프라인 개요
CI/CD 파이프라인은 GitHub 저장소에 `feature` 단위의 `merge`를 받은 `backend`, `front` 두 브랜치 각각에 연동됩니다.
두 브랜치에 `push`라는 이벤트가 발생할 때마다 자동으로 트리거됩니다.
프론트엔드와 백엔드는 각각의 Jenkins 파이프라인에 의해 처리되며, Docker 이미지를 생성하여 Kubernetes 클러스터에 배포합니다.

파이프라인은 다음과 같은 주요 단계로 구성됩니다.

#### 3. CI/CD 파이프라인 단계

##### 1) GitHub 웹훅 트리거

- **상황**: 개발자가 GitHub에 코드를 푸시하거나 Pull Request(PR)를 생성하면, GitHub에 설정된 웹훅이 이 이벤트를 감지합니다. 웹훅은 GitHub 리포지토리에서 발생한 특정 이벤트를 외부 시스템으로 전달하는 역할을 합니다.
- **과정**: GitHub에서 발생한 이벤트는 설정된 웹훅을 통해 Jenkins 서버로 전달됩니다. Jenkins 서버는 이 이벤트를 수신하고, 이를 기반으로 미리 정의된 CI/CD 파이프라인을 자동으로 시작합니다.
- **결과**: Jenkins는 GitHub 웹훅에서 전달된 이벤트를 감지하고, 해당 프로젝트의 빌드 및 배포를 위한 파이프라인을 트리거하여, 자동화된 프로세스를 시작하게 됩니다. 이로 인해 개발자는 수동 개입 없이 코드 변경 사항이 자동으로 빌드 및 배포 과정에 반영되는 것을 보장할 수 있습니다.

##### 2) 코드 체크아웃
- **상황**: Jenkins 파이프라인이 시작되면, 첫 번째 단계로 GitHub 저장소에서 최신 코드를 가져옵니다.
- **결과**: 프론트엔드와 백엔드 각각에 대해 코드가 Jenkins 워크스페이스로 체크아웃됩니다.

##### 3) 빌드
- **프론트엔드**:
    - **상황**: Jenkins는 Node.js 환경에서 `npm install`을 실행하여 필요한 의존성을 설치한 후, `npm run build` 명령어를 사용해 빌드합니다.

- **백엔드**:
    - **상황**: Jenkins는 Gradle을 사용해 백엔드 애플리케이션을 빌드합니다.

##### 4) Docker 이미지 빌드 및 푸시
- **상황**: 빌드가 완료된 후, Jenkins는 Docker 이미지를 생성합니다. Dockerfile을 기반으로 각각의 애플리케이션(프론트엔드, 백엔드)에 대해 이미지를 빌드합니다.
- **결과**: 빌드된 Docker 이미지는 지정된 Docker Hub로 푸시됩니다. 이때 각 이미지의 tage name은 Jenkins 빌드 횟수를 기준으로 지정됩니다.

##### 5) Kubernetes Canary 무중단 배포
- **상황**:  Jenkins는 Kubernetes 클러스터에 해당 이미지를 배포합니다. 각 파이프라인은 Kubernetes YAML 파일(`k8s-frontend.yml`, `k8s-backend.yml`)을 사용해 Deployment와 Service, Ingress Controller를 생성합니다.
- **과정**: Jenkins 파이프라인이 새로운 애플리케이션 버전을 배포할 때, Ingress Controller에 weight 값을 설정하여 트래픽의 일부만 새 버전으로 라우팅되도록 설정합니다. 초기 테스트에서 문제가 발견되지 않으면, weight 값을 점진적으로 증가시켜 더 많은 트래픽을 새로운 버전으로 전환합니다. 모든 테스트와 모니터링에서 문제가 없다고 판단되면, 최종적으로 모든 트래픽을 새로운 버전으로 전환합니다
- **결과**: Kubernetes 클러스터에서 새로운 버전의 애플리케이션이 무중단으로 배포되며, 이전 버전은 점진적으로 제거됩니다. 최신 이미지를 사용한 애플리케이션이 안정적으로 전체 사용자에게 배포됩니다.

<br>
<br>

# 📑 Canary 무배포 방식 선택 이유

--- 

### 1. 사용자 피드백과 Canary 배포의 연관성

#### 1) 사용자 피드백의 중요성
- 일정 관리 시스템은 사용자의 일상적인 업무의 편리성과 효율성을 제공하기 위한 목적을 가지고 있습니다. 따라서, 제품으로서의 가치는 사용자에게 얼마나 편리하게 접근되는지가 큰 영향을 미치고, 업데이트의 목적은 사용자 만족도 유지와 향상, 업무 효율성 저해 방지가 됩니다. 이는 기능 추가 등의 사유로 기존의 화면 구성이 변경되어 새로운 업데이트를 도입하는 경우 이용자의 즉각적인 피드백이 중요하게 작용하는 이유가 됩니다.

#### 2) 단계적 배포로 인한 영향범위 제한
- Rolling 배포나 Blue-Green 배포와 달리, Canary 배포는 소수의 사용자에게 먼저 배포함으로써, 문제가 발생할 경우 그 영향 범위를 제한할 수 있습니다. 사용자 피드백을 빠르게 수집하고, 문제가 발생할 경우 빠르게 대응할 수 있는 장점을 가지고 있습니다. 더불어 문제가 발생할 경우 신속하게 해당 변경 사항을 롤백하거나 수정할 수 있습니다. 이로 인해 전체 시스템에 미치는 영향을 최소화하고, 다른 사용자에게는 문제가 발생하지 않도록 할 수 있습니다.

#### 4) A/B 테스트와의 결합 가능성
- Canary 배포는 새로운 기능의 효과를 제한된 사용자 그룹에서 먼저 테스트할 수 있는 A/B 테스트와 결합하여 사용할 수 있습니다. 예를 들어, 새로운 일정 관리 기능이나 인사 관리 프로세스 개선을 도입할 때, 일부 사용자에게만 이를 노출하고, 그들의 피드백을 수집하여 최적화할 수 있습니다. 이를 통해 최종 사용자에게 배포하기 전에 기능을 더 정교하게 다듬고, 조직 내에서의 수용성을 높일 수 있습니다.

### 2. 다른 배포 방식과 비교한 Canary 배포의 선택 이유

#### vs. Blue-Green 배포
- **사용자 피드백의 반영**: Blue-Green 배포는 두 개의 완전한 환경을 유지하면서 전체 트래픽을 한 번에 새로운 버전으로 전환하는 방식입니다. 하지만 이 경우 사용자 피드백을 즉시 반영하기 어렵고, 문제가 발생하면 전체 사용자에게 영향을 미칠 수 있습니다. 반면, Canary 배포는 일부 사용자에게만 배포되어 피드백을 수집하고, 필요 시 빠르게 대응할 수 있습니다.

#### vs. Rolling 배포
- **세밀한 제어**: Rolling 배포는 모든 사용자에게 새로운 버전을 점진적으로 배포하지만, Canary 배포처럼 특정 사용자 그룹을 지정하여 피드백을 집중적으로 받을 수 있는 기능은 부족합니다. 일정 관리 시스템에서는 특정 부서나 역할을 가진 사용자에게만 새로운 기능을 배포하고 피드백을 받는 것이 중요할 수 있으며, 이 점에서 Canary 배포가 더 유리합니다.

#### vs. A/B 테스트
- **기능 검증과 안정성**: A/B 테스트는 주로 기능의 효과나 사용자 선호도를 테스트하는 데 사용되며, Canary 배포는 시스템 전체의 안정성을 보장하는 데 중점을 둡니다. 인사 관리와 일정 관리 시스템에서는 새로운 기능의 안정성과 성능이 중요하므로, 이러한 측면에서 Canary 배포가 더 적합합니다.

# 📑 기능 테스트

<details>
  <summary><b>Git push 쿠버네티스 리소스 생성</b></summary>
  <div markdown="1">
   <br>
    <ul>
    
![git-push-후-파드-생성 (1)](https://github.com/user-attachments/assets/309a3a33-3b87-404d-b742-c66d4f1a7abc)

  </div>
</details>

<details>
  <summary><b>Canary 무중단 배포 버전 변환</b></summary>
  <div markdown="1">
   <br>
    <ul>
	    
![버전 변한 카나리](https://github.com/user-attachments/assets/82102f83-5411-4430-88be-574a70e920df)
 
  </div>
</details>

--- 

### ✨BACKEND

<details>
  <summary><b>Canary 무중단 배포</b></summary>
  <div markdown="1">
   <br>
    <ul>
	    
![backend-카나리-무중단 (1)](https://github.com/user-attachments/assets/aa3ffb66-e468-4b77-a8c2-0e6459a710ec)
	    

  </div>
</details>

<details>
  <summary><b>Pipeline</b></summary>
  <div markdown="1">
   <br>
    <ul>
	

https://github.com/user-attachments/assets/2b9b16bf-56cd-4262-a3e1-054f0c81f271

    

  </div>
</details>

--- 

### ✨FRONTEND

<details>
  <summary><b>Canary 무중단 배포</b></summary>
  <div markdown="1">
   <br>
    <ul>
	    
![front-카나리-무중단 (1)](https://github.com/user-attachments/assets/6f754854-7198-4af1-948e-6200ca30e075)

  </div>
</details>

<details>
  <summary><b>Pipeline</b></summary>
  <div markdown="1">
   <br>
    <ul>
	    
![프론트 파이프라인](https://github.com/user-attachments/assets/b939d0ca-6acc-4e09-be72-1b6879dc11b2)


  </div>
</details>
