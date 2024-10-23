![header](https://capsule-render.vercel.app/api?type=venom&color=auto&height=300&section=header&text=CalIT&fontSize=50&desc=📆Optimize%20Your%20Workspace%20Scrum%20Management&descAlignY=60)

# 👀 팀원 구성

<br>

|                    **👑차윤슬**                      |                  **박성준**                   |                 **최승은**                  |                     **강혜정**                     |             **지연희**              |
|:------------------------------------------------:|:------------------------------------------:|:----------------------------------------:|:-----------------------------------------------:|:--------------------------------:|
| <img src="https://github.com/user-attachments/assets/8d255376-5ae9-4685-8f11-cd4b18a4bb55" width="128px"/> | <img src="https://github.com/user-attachments/assets/f49055cf-2b4d-41ac-bb7d-98b47d257c4b" width="128px"/> | <img src="https://github.com/user-attachments/assets/21e6cee5-c2f0-4c94-9a0a-938053c5342b" width="128px"/> | <img src="https://github.com/user-attachments/assets/813020ee-ef97-4f44-becd-38ef55a778b1" width="128px"/> | <img src="https://github.com/user-attachments/assets/cefb90f7-237b-4613-b6e2-89e1c40c00f3" width="128px"/> |
| [@yunseul-dev](https://github.com/yunseul-dev) | [@seongxun](https://github.com/seongxun) | [@xeunnie](https://github.com/xeunnie) | [ @hyejeung](https://github.com/hyejeung) | [@Aqulog](https://github.com/Aqulog) |


<br/>

## ⚒️ 기술 스택

### CI/CD
<img src="https://img.shields.io/badge/Git-000?style=style&logo=Git&logoColor=F05032&color=white" alt=""> <img src="https://img.shields.io/badge/GitHub-181717?style=flat&logo=GitHub&logoColor=black&color=white" alt=""> <img src="https://img.shields.io/badge/Jenkins-D24939?style=flat&logo=jenkins&logoColor=D24939&color=white" alt=""> <img src="https://img.shields.io/badge/Docker-2496ED?style=flat&logo=Docker&logoColor=2496ED&color=white" alt=""> <img src="https://img.shields.io/badge/Kubernetes-326CE5?style=flat&logo=Kubernetes&logoColor=326CE5&color=white" alt="">
### 모니터링
<img src="https://img.shields.io/badge/Prometheus-181717?style=flat&logo=Prometheus&logoColor=E6522C&color=white" alt=""> <img src="https://img.shields.io/badge/Grafana-181717?style=flat&logo=Grafana&logoColor=F46800&color=white" alt="">

<br>

## 📆 CalIT 운영 환경

![CalIT 운영 환경](https://github.com/user-attachments/assets/72eb170f-9e90-4a0b-9734-fdc686eb67dc)


| 항목                | 설명                                                                                                                                                   |
|---------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|
| **운영 환경**       | **CentOS 8** 기반의 리눅스 가상 머신 **5대**로 구성된 Kubernetes 클러스터 <br> (Master Node **1대**, Worker Node **4대**)                               |
| **네트워크 구성**   | 네트워크 장치를 **브릿지(Bridge) 모드**로 설정하여 모든 노드가 동일한 네트워크 세그먼트에서 통신                                                          |
| **서비스 구성**     | **Ingress 구조**를 사용하여 외부 트래픽을 관리하며, 내부 서비스는 **Cluster IP**를 사용해 내부적으로만 접근 가능 <br> 서비스 통신은 `(app: calit-frontend-v1)`과 같은 라벨로 구성  |
| **Cluster IP**      | Kubernetes 클러스터 내에서만 유효한 IP로 **내부 서비스 간 통신**을 제공하며, 외부 트래픽은 Ingress를 통해 관리                                               |
| **Ingress**         | **외부 요청을 클러스터 내부 서비스**로 라우팅하며, 서비스 접근을 효율적으로 관리하고 외부와의 인터페이스를 제공                                            |
| **모니터링 시스템** | **Prometheus**와 **Grafana**를 통해 클러스터와 애플리케이션 상태를 모니터링 및 시각화                                                                     |


### 🖥️ 추가 사항

내용

<br>

### 🖥️ 모니터링 시스템
![캘릿 추후 변경 예정](https://github.com/user-attachments/assets/d9d66bf1-9b2c-44cc-aa77-5f88c886f89e)

CalIT은 Kubernetes 클러스터의 성능을 배치 작업 전후로 비교하기 위해 Prometheus, Node Exporter, Grafana를 활용하여 시스템 메트릭을 수집하고 시각화한다. 각 구성 요소는 다음과 같은 역할을 수행한다:

- **Prometheus**:
  - Node Exporter로부터 수집한 CPU, 메모리, 디스크 사용량 등 다양한 성능 메트릭을 저장한다.
  - 수집된 데이터를 **Grafana**와 연동하여 시각화를 위한 데이터를 제공한다.
  
- **Node Exporter**:
  - 각 노드의 성능 지표(예: CPU, 메모리, 디스크 사용량 등)를 **Prometheus**에 전송한다.
  
- **Grafana**:
  - **Prometheus**로부터 수집된 메트릭 데이터를 사용하여 대시보드를 구성하고, 사용자가 클러스터 상태를 직관적으로 이해할 수 있도록 시각화한다.
  - 실시간 대시보드를 제공함으로써 클러스터의 상태를 한눈에 모니터링할 수 있게 한다.
  
#### 📝 주요 활용 시나리오
- **성능 비교 분석**: 배치 작업 수행 전과 후의 성능 변화를 비교하고 분석하여 클러스터의 효율성을 평가한다.
- **문제 탐지**: 실시간 모니터링을 통해 클러스터 내 발생할 수 있는 문제를 신속하게 탐지하고 대응할 수 있도록 지원한다.

<br>

## ⛓️‍💥 젠킨스 파이프라인
![백엔드 배포 시스템 (1)](https://github.com/user-attachments/assets/9e669be0-a091-4495-ace9-4e03512e4f5e)

<details>
  <summary><b>백엔드 배포 시나리오</b></summary>
  <div markdown="1">

🛠 백엔드 배포 시나리오

	1.	개발자가 백엔드 코드의 변경 사항을 GitHub에 푸시
	2.	GitHub에서 Webhook을 통해 Jenkins로 알림 전송
	3.	Jenkins 파이프라인 실행
 
	•	Git Clone: Jenkins가 GitHub에서 프로젝트 코드 클론
	•	Project Clean: 프로젝트를 정리하고, 불필요한 파일 제거
	•	Project Compile: 코드 컴파일
	•	Project Test: 코드 테스트 진행
	•	Project Build: 테스트가 성공하면 프로젝트 빌드 완료
	•	Docker Build: 빌드된 프로젝트를 기반으로 Docker 이미지 생성
	•	Docker Push: 생성된 Docker 이미지를 Docker Hub로 푸시
 
	4.	K8S 마스터는 Docker Hub에서 업데이트된 이미지를 받아 백엔드 서버에 배포
	5.	배포가 성공적으로 완료되면 Jenkins가 Discord를 통해 배포 성공 알림을 전송(만약 배포가 실패할 경우, Discord에 실패 알림이 전송)


  </div>
</details>

<br>

<details>
  <summary><b>프론트엔드 배포 시나리오</b></summary>
  <div markdown="1">

  🛠 프론트엔드 배포 시나리오

	1.	개발자가 프론트엔드 코드의 변경 사항을 GitHub에 푸시합니다.
	2.	GitHub에서 Webhook을 통해 Jenkins로 알림 전송
	3.	Jenkins 파이프라인 실행
 
	•	Git Clone: Jenkins가 GitHub에서 프론트엔드 프로젝트 코드 클론
	•	Project Clean: 프로젝트를 정리하고, 불필요한 파일 제거
	•	Project Compile: 프론트엔드 코드 컴파일
	•	Project Test: 컴파일된 코드에 대한 테스트 진행
	•	Project Build: 테스트가 성공하면 프로젝트 빌드 완료
	•	Docker Build: 빌드된 프로젝트를 기반으로 Docker 이미지 생성
	•	Docker Push: 생성된 Docker 이미지를 Docker Hub로 푸시
 
	4.	K8S 마스터는 Docker Hub에서 업데이트된 이미지를 받아 프론트엔드 서버에 배포합니다.
	5.	배포가 성공적으로 완료되면 Jenkins가 Discord를 통해 배포 성공 알림을 전송(만약 배포가 실패할 경우, Discord에 실패 알림이 전송)

  </div>
</details>

<br>

<details>
  <summary><b>Canary 무중단 배포</b></summary>
  <div markdown="1">

내용


  </div>
</details>



