import user1 from "@/assets/icon/persona/user1.svg";
import user2 from "@/assets/icon/persona/user2.svg";
import user3 from "@/assets/icon/persona/user3.svg";
import user4 from "@/assets/icon/persona/user4.svg";
import user5 from "@/assets/icon/persona/user5.svg";

    export const chatRoomList = [
        {
            chatroomId: 1,
            chatRoomName: '최승은',
            messageContents: '빠샤샤 🔥',
            createdAt: '2024-09-10T16:45:04.930487',
            unreadMessages: 4,
            profilePic: user1
        },
        {
            chatroomId: 2,
            chatRoomName: '박성준',
            messageContents: '메롱롱',
            createdAt: '2024-09-10T16:45:04.930487',
            unreadMessages: 0,
            profilePic: user2
        },
        {
            chatroomId: 3,
            chatRoomName: '차윤슬',
            messageContents: " 아쟈쟈",
            createdAt: '2024-09-10T16:45:04.930487',
            profilePic: user3
        },
        {
            chatroomId: 4,
            chatRoomName: '지연희',
            messageContents: '귀오밍',
            createdAt: '2024-09-10T16:45:04.930487',
            profilePic: user4
        },
        {
            chatroomId: 5,
            chatRoomName: '강혜정',
            messageContents: '오구오구',
            createdAt: '2024-09-10T16:45:04.930487',
            profilePic: user5
        },
    ]


export const chat = [
    [
        {
            "messageId": 1,
            "senderId": 6,
            "userName": "지연희",
            "messageContents": "4시 44분.",
            "createdAt": "2024-09-10T16:45:00.406759",
            "messageType": "TEXT",
            "messageStatus": "UNREAD",
            "persona": null,
            "file" : {
                "fileType": "PDF",
                "fileSize": "2mb",
                "fileUrl": "https://daqu2024-s3.s3.ap-northeast-2.amazonaws.com/3b8f0f3d-4e9b-4f2a-8d9b-3e6f6f7f4b6b_2024-09-10-16-45-36.pdf",
                "fileName": "2024-09-10-16-45-36.pdf",
            }
        },
        {
            "messageId": 2,
            "senderId": 6,
            "userName": "지연희",
            "messageContents": "4시 44분1.",
            "createdAt": "2024-09-10T16:45:04.930487",
            "messageType": "TEXT",
            "messageStatus": "UNREAD",
            "persona": null,
            "file" : {
                "fileType": "code",
                "fileSize": "2mb",
                "fileUrl": "https://daqu2024-s3.s3.ap-northeast-2.amazonaws.com/3b8f0f3d-4e9b-4f2a-8d9b-3e6f6f7f4b6b_2024-09-10-16-45-36.py",
                "fileName": "2024-09-10-16-45-36.py",
            }
        },
        {
            "messageId": 3,
            "senderId": 6,
            "userName": "지연희",
            "messageContents": "4시 44분2.",
            "createdAt": "2024-09-10T16:45:07.351809",
            "messageType": "TEXT",
            "messageStatus": "UNREAD",
            "persona": null,
            "file" : {
                "fileType": "JPG",
                "fileSize": "3mb",
                "fileUrl": "https://daqu2024-s3.s3.ap-northeast-2.amazonaws.com/3b8f0f3d-4e9b-4f2a-8d9b-3e6f6f7f4b6b_Screenshot-3817.jpg",
                "fileName": "Screenshot-3817.jpg",
            }
        },
        {
            "messageId": 4,
            "senderId": 6,
            "userName": "지연희",
            "messageContents": "4시 44분3.",
            "createdAt": "2024-09-10T16:45:09.572095",
            "messageType": "TEXT",
            "messageStatus": "UNREAD",
            "persona": null,
            "file" : {
                "fileType": "docx",
                "fileSize": "1mb",
                "fileUrl": "https://daqu2024-s3.s3.ap-northeast-2.amazonaws.com/3b8f0f3d-4e9b-4f2a-8d9b-3e6f6f7f4b6b_2024-09-10-16-45-36.docx",
                "fileName": "2024-09-10-16-45-36.docx",
            }
        },
        {
            "messageId": 5,
            "senderId": 6,
            "userName": "지연희",
            "messageContents": "4시 44분4.",
            "createdAt": "2024-09-10T16:45:36.417615",
            "messageType": "TEXT",
            "messageStatus": "UNREAD",
            "persona": null,
            "file" : {
                "filType" : "PDF",
                "fileSize" : "2mb",
                "fileUrl" : "https://daqu2024-s3.s3.ap-northeast-2.amazonaws.com/3b8f0f3d-4e9b-4f2a-8d9b-3e6f6f7f4b6b_2024-09-10-16-45-36.pdf",
                "fileName" : "2024-09-10-16-45-36.pdf",
            }
        },
        {
            "messageId": 6,
            "senderId": 6,
            "userName": "지연희",
            "messageContents": null,
            "createdAt": "2024-09-10T16:45:43.901013",
            "messageType": "FILE",
            "messageStatus": "UNREAD",
            "persona": null,
            "file": {
                "fileType": "PNG",
                "fileSize": "4mb",
                "fileUrl": "https://daqu2024-s3.s3.ap-northeast-2.amazonaws.com/e7cf2554-d9be-40fd-9d81-31d1c21b2430_Screenshot-3817.png",
                "fileName": "Screenshot-3817.png",
            }
        }
    ]
]