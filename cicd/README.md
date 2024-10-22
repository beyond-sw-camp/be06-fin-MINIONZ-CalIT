![header](https://capsule-render.vercel.app/api?type=venom&color=auto&height=300&section=header&text=CalIT&fontSize=50&desc=Optimize%20Your%20Workspace%20Scrum%20Management&descAlignY=60)

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
![캘릿 추후 변경 예정](https://github.com/user-attachments/assets/5fc18c69-96fc-4415-99df-f7eca2411e3c)

| 항목                | 설명                                                                                                                                                   |
|---------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------|
| **운영 환경**       | **CentOS 8** 기반의 리눅스 가상 머신 **5대**로 구성된 Kubernetes 클러스터 <br> (Master Node **1대**, Worker Node **4대**)                               |
| **네트워크 구성**   | 네트워크 장치를 **브릿지(Bridge) 모드**로 설정하여 모든 노드가 동일한 네트워크 세그먼트에서 통신                                                          |
| **서비스 구성**     | **Ingress 구조**를 사용하여 외부 트래픽을 관리하며, 내부 서비스는 **Cluster IP**를 사용해 내부적으로만 접근 가능 <br> 서비스 통신은 `(app: calit-frontend-v78)`과 같은 라벨로 구성  |
| **Cluster IP**      | Kubernetes 클러스터 내에서만 유효한 IP로 **내부 서비스 간 통신**을 제공하며, 외부 트래픽은 Ingress를 통해 관리                                               |
| **Ingress**         | **외부 요청을 클러스터 내부 서비스**로 라우팅하며, 서비스 접근을 효율적으로 관리하고 외부와의 인터페이스를 제공                                            |
| **모니터링 시스템** | **Prometheus**와 **Grafana**를 통해 클러스터와 애플리케이션 상태를 모니터링 및 시각화                                                                     |


### 🖥️ 추가 사항

내용

<br>

### 🖥️ 모니터링 시스템
![캘릿 추후 변경 예정](https://github.com/user-attachments/assets/d9d66bf1-9b2c-44cc-aa77-5f88c886f89e)

내용

<br>

## ⛓️‍💥 젠킨스 파이프라인
![캘릿 추후 변경 예정](https://github.com/user-attachments/assets/e2b26a94-5d7c-4caa-bcac-a968d11bf796)

<details>
  <summary><b>백엔드 배포 시나리오</b></summary>
  <div markdown="1">

내용


  </div>
</details>

<br>

<details>
  <summary><b>프론트엔드 배포 시나리오</b></summary>
  <div markdown="1">

내용


  </div>
</details>

<br>

<details>
  <summary><b>Canary 무중단 배포</b></summary>
  <div markdown="1">

내용


  </div>
</details>



