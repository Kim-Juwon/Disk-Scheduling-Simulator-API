# Disk-Scheduling-Simulator-API
디스크 Seek time 스케줄링 시뮬레이터 API

## What is Disk Scheduling?
- Disk 스케줄링은 disk drive가 disk pack에서 데이터를 처리하는 요청에 대해 처리 순서를 결정하는 작업입니다.
- 목적은 성능 향상에 있습니다.
  - throughput (단위 시간당 처리량)
  - mean response time (평균 응답 시간)
  - predictability (응답 시간의 예측 가능성) 
- Disk data access time
  - `seek time`
    - disk head가 목표 cylinder로 이동하는 시간  
  - `rotational delay`, 
    - 목표 sector에 head를 위치시키기 위해 spindle을 회전하는 시간
  - `data transmission time`
    - disk head가 목표 sector을 읽어서 처리(전송 or 기록)하는 시간  
- 이 프로젝트는 seek time을 최적화하는 스케줄링 알고리즘들에 대한 시뮬레이터 API입니다.

## 프로젝트 개발 계기
- 대학교 운영체제 수업에서 seek time optimizing 알고리즘들을 이론적으로 배웠습니다. 수업은 끝났지만 개인적으로 실제로 구현하여 시뮬레이터를 만들어보고 싶었고, 이를 통해 각 알고리즘의 동작과 철학을 더 깊게 이해하고 싶었습니다. 또한 Java 언어와도 더 친숙해지고자 프로젝트를 진행하게 되었습니다. 

## Seek Time Optimizing Algorithms
FCFS, SSTF, Scan, C-Scan, Look, C-Look

### `FCFS (First Come First Service)`
- 요청된 순서대로 요청을 처리하는 알고리즘입니다.
- 장점
  - 공평한 처리  
  - no starvation
  - simple (low scheduling overhead)
- 단점
  - 성능 최적화에 대한 고려가 없음 (따라서 disk access 부하가 적은 경우 적합)

### `SSTF (Shortest Seek Time First)`
- 현재 head 위치에서 가장 가까운 cylinder에 대한 요청을 먼저 처리
- 장점
  - throughput 증가 (batch system에 적합)
  - mean response time 감소
- 단점
  - starvation 현상 발생 가능
  - predictability 감소 (starvation이 발생하는 요청들은 언제 처리될지 예측하기 어려움)   

### 
