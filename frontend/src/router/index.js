import { createRouter, createWebHistory } from "vue-router";
import UserTemplate from "@/view/user/UserTemplate.vue";
import LoginPage from "@/view/user/pages/LoginPage.vue";
import SignupPage from "@/view/user/pages/SignupPage.vue";
import PasswordPage from "@/view/user/pages/PasswordPage.vue";
import CompletePage from "@/view/user/pages/CompletePage.vue";
import {workspaceStore} from "@/stores/workspace/useWorkspaceStore";

const routes = [
    {
        path: '/',
        name: 'Thumbnail',
        component: () => import('@/view/thumbnail/ThumbnailPage.vue')
    },

    // user
    {
        path: '/user',
        name: 'UserTemplate',
        component: UserTemplate,
        children: [
            {
                path: 'login',
                name: 'Login',
                component: LoginPage
            },
            {
                path: 'signup',
                name: 'Signup',
                component: SignupPage
            },
            {
                path: 'password',
                name: 'Password',
                component: PasswordPage
            },
            {
                path: 'password-reset',
                name: 'PasswordReset',
                component: () => import('@/view/user/pages/PasswordResetPage.vue')
            },
            {
                path: 'complete',
                name: 'Complete',
                component: CompletePage
            }
        ]
    },

    // my
    {
        path: '/my',
        name: 'My',
        component: () => import('@/layouts/ContentsArea.vue'),
        children: [
            {
                path: 'dashboard',
                name: 'MyDashboard',
                component: () => import('@/view/dashboard/MyDashBoard.vue')
            },
            {
                path: 'schedule',
                name: 'MySchedule',
                children: [
                    {
                        path: 'monthly',
                        name: 'MyMonthly',
                        component: () => import('@/view/schedule/monthly/MyMonthly.vue')
                    },
                    {
                        path: 'weekly',
                        name: 'myWeekly',
                        component: () => import('@/view/schedule/weekly/MyWeekly.vue')
                    }
                ]
            },
            {
                path: 'task',
                name: 'MyTask',
                component: () => import('@/view/scrum/Task/TaskTemplate.vue'),
                children: [
                    {
                        path: 'kanban',
                        name: 'MyTaskKanban',
                        component: () => import('@/view/scrum/Task/kanban/MyTaskKanban.vue')
                    },
                    {
                        path: 'list',
                        name: 'MyTaskList',
                        component: () => import('@/view/scrum/Task/list/MyTaskList.vue')
                    },
                ]
            }
        ]
    },

    // dashboard
    {
        path: '/workspace/:workspaceId',
        name: 'Workspace',
        component: () => import('@/layouts/ContentsArea.vue'),
        children: [
            // dashboard
            {
                path: 'dashboard',
                name: 'WorkspaceDashboard',
                component: () => import('@/view/dashboard/WorkspaceDashBoard.vue')
            },
            // schedule
            {
                path: 'schedule',
                name: 'WorkspaceSchedule',
                children: [
                    {
                        path: 'monthly',
                        name: 'WorkspaceMonthly',
                        component: () => import('@/view/schedule/monthly/WorkSpaceMonthly.vue')
                    },
                    {
                        path: 'weekly',
                        name: 'WorkspaceWeekly',
                        component: () => import('@/view/schedule/weekly/WorkSpaceWeekly.vue')
                    }
                ]
            },

            // scrum
            {
                path: 'scrum',
                name: 'WorkspaceScrum',
                children: [
                    {
                        path: 'dashboard',
                        name: 'WorkspaceScrumWorkspaceCreate',
                        component: () => import('@/view/scrum/create/WorkSpaceCreate.vue')
                    },
                    {
                        path: 'label',
                        name: 'WorkspaceScrumLabel',
                        component: () => import('@/view/scrum/list/LabelList.vue')
                    },
                    {
                        path: 'sprint',
                        name: 'WorkspaceScrumSprint',
                        children: [
                            {
                                path: 'list',
                                name: 'WorkspaceScrumSprintList',
                                component: () => import('@/view/scrum/list/SprintList.vue')
                            },
                            {
                                path: 'create',
                                name: 'WorkspaceScrumSprintCreate',
                                component: () => import('@/view/scrum/create/SprintCreate.vue')
                            }
                        ]
                    },
                    {
                        path: 'task',
                        name: 'WorkspaceScrumTask',
                        component: () => import('@/view/scrum/Task/TaskTemplate.vue'),
                        children: [
                            {
                                path: 'kanban',
                                name: 'WorkspaceTaskKanban',
                                component: () => import('@/view/scrum/Task/kanban/WorkSpaceTaskKanban.vue')
                            },
                            {
                                path: 'list',
                                name: 'WorkspaceTaskList',
                                component: () => import('@/view/scrum/Task/list/WorkSpaceTaskList.vue')
                            },
                            {
                                path: 'timeline',
                                name: 'WorkspaceTaskTimeline',
                                component: () => import('@/view/scrum/Task/timeline/TimeLine.vue')
                            },
                        ]
                    },
                    {
                        path: 'meeting',
                        name: 'BoardMeeting',
                        children: [
                            {
                                path: 'list',
                                name: 'BoardMeetingList',
                                component: () => import('@/view/scrum/meeting/MeetingList.vue')
                            },
                            {
                                path: 'detail/:id',
                                name: 'BoardMeetingDetail',
                                component: () => import('@/view/scrum/meeting/MeetingDetail.vue')
                            },
                            {
                                path: 'create',
                                name: 'BoardMeetingCreate',
                                component: () => import('@/view/scrum/meeting/MeetingCreate.vue')
                            }
                        ]
                    },
                    {
                        path: 'board',
                        name: 'Board',
                        children: [
                            {
                                path: 'error',
                                name: 'Error',
                                children: [
                                    {
                                        path: 'list',
                                        name: 'ErrorList',
                                        component: () => import('@/view/board/list/ErrorList.vue')
                                    },
                                    {
                                        path: 'detail/:id',
                                        name: 'ErrorDetail',
                                        component: () => import('@/view/board/detail/ErrorDetail.vue')
                                    },
                                    {
                                        path: 'create',
                                        name: 'ErrorCreate',
                                        component: () => import('@/view/board/create/ErrorCreate.vue')
                                    }
                                ]
                            },
                            {
                                path: 'qa',
                                name: 'QA',
                                children: [
                                    {
                                        path: 'list',
                                        name: 'QAList',
                                        component: () => import('@/view/board/list/QAList.vue')
                                    },
                                    {
                                        path: 'detail/:id',
                                        name: 'QADetail',
                                        component: () => import('@/view/board/detail/QADetail.vue')
                                    },
                                    {
                                        path: 'create',
                                        name: 'QACreate',
                                        component: () => import('@/view/board/create/QACreate.vue')
                                    }
                                ]
                            }
                        ]
                    }
                ]
            },
            {
                path: 'chat',
                name: 'Chat',
                component: () => import('@/view/chat/ChatInitialPage.vue'),
            },
            {
                path: 'chat/:id',
                name: 'ChatRoom',
                component: () => import('@/view/chat/ChatRoomPage.vue')
            }
        ]
    },
];

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
});

router.beforeEach((to, from, next) => {
    if (to.params.workspaceId) {
        workspaceStore.setWorkspaceId(to.params.workspaceId);
    }
    next();
});

export default router;