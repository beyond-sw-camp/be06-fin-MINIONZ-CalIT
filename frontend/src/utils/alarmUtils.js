import { Notyf } from 'notyf';
import { useUserStore } from '@/stores/user/useUserStore';
import router from '@/router';

const notyf = new Notyf();
const userStore = useUserStore();
const userId = userStore.user.value?.idx;
let eventSource;

export function connectEventSource() {
  if (eventSource) {
    eventSource.close();
  }

  if (
    userId &&
    (router.currentRoute.value.path.startsWith('/my') ||
      router.currentRoute.value.path.startsWith('/workspace'))
  ) {
    eventSource = new EventSource(
      `https://calit.kro.kr/api/alarm/connect/${userId}`
    );
  }

  if (eventSource) {
    eventSource.onmessage = (event) => {
      const data = JSON.parse(event.data);
      notyf.success(data.AlarmContents);
    };
  }

  if (eventSource) {
    eventSource.onerror = () => {
      console.error('SSE 연결이 끊어졌습니다. 재연결 시도 중...');
      eventSource.close();
      setTimeout(connectEventSource, 10000);
    };
  }
}
